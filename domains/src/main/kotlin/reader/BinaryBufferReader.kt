package com.jwhi.som.domains.reader

import com.jwhi.som.domains.InvalidEvtException
import com.jwhi.som.domains.custom.MapEvent
import com.jwhi.som.domains.evt.CompareType
import com.jwhi.som.domains.evt.ComparisonType
import com.jwhi.som.domains.evt.EvtCondition
import com.jwhi.som.domains.evt.EvtDefinition
import com.jwhi.som.domains.evt.EvtPage
import com.jwhi.som.domains.evt.TargetType
import com.jwhi.som.domains.evt.TriggerType
import com.jwhi.som.domains.evt.parseEvtOperation
import com.jwhi.som.domains.helpers.asBufferLittleEndian
import com.jwhi.som.domains.helpers.getEvtOperationsBuffers
import java.nio.ByteBuffer

class BinaryBufferReader(val buffer: ByteBuffer) {
    constructor(fileName: String) : this(
            BinaryBufferReader::class.java.getResourceAsStream(fileName)
                .readAllBytes()
                .asBufferLittleEndian()
    )

    fun getEvtDefinitions(): List<EvtDefinition> {
        val magicHeader = buffer.getInt().toUInt()
        if (magicHeader != 1024u) {
            throw InvalidEvtException("Invalid header. Expected 1024 but was $magicHeader")
        }
        val events =  (1..251).map {
            readEvtDefinition()
        }
        return events
    }

    fun readEvtDefinition(): EvtDefinition {
        // Name is 00 to 1E
        val nameBytes = ByteArray(31)
        buffer.get(nameBytes)
        val name = String(nameBytes, Charsets.UTF_8).trim(0x00.toChar())
        val targetType = TargetType.from(buffer.get().toUByte())
        val targetId = buffer.getShort()
        val triggerType = TriggerType.from(
            buffer.get().toUByte()
        )
        val triggerItem = buffer.get().toUByte()
        val triggerCone = buffer.getShort().toUShort()
        val padding = buffer.getShort().toUShort()
        val triggerRectWE = buffer.getFloat()
        val triggerRectNS = buffer.getFloat()

        val triggerRadius = buffer.getFloat()

        val condition = EvtCondition(
            compareType = CompareType.from(
                buffer.getShort().toUShort()
            ),
            compareId = buffer.getShort().toUShort(),
            comparedValue = buffer.getShort().toUShort(),
            comparisonType = ComparisonType.from(
                buffer.getShort().toUShort()
            )
        )

        val pages = (1..16).map {
            val payloadOffset = buffer.getInt().toUInt()
            val compareType = CompareType.from(
                buffer.getShort().toUShort()
            )
            val compareId = buffer.getShort().toUShort()
            val comparedValue = buffer.getShort().toUShort()
            val comparisonType = ComparisonType.from(
                buffer.getShort().toUShort()
            )
            EvtPage(
                payloadOffset = payloadOffset,
                startCondition = EvtCondition(
                    compareType = compareType,
                    compareId = compareId,
                    comparedValue = comparedValue,
                    comparisonType = comparisonType
                )
            )
        }.filter { it.payloadOffset != 0u }

        return EvtDefinition(
            name = name,
            targetType = targetType,
            targetId = targetId,
            triggerType = triggerType,
            triggerItem = triggerItem,
            triggerCone = triggerCone,
            padding = padding.toHexString().toByteArray().toList(),
            triggerRectWE = triggerRectWE,
            triggerRectNS = triggerRectNS,
            triggerRadius = triggerRadius,
            condition = condition,
            pages = pages
        )
    }

    fun readMapEvent(): List<MapEvent> {
        val evtDefinitions = getEvtDefinitions()
        return evtDefinitions.map { definition ->
            MapEvent(
                definition = definition,
                pageOperations = definition.pages.associate { evtPage ->
                    this.buffer.position(evtPage.payloadOffset.toInt())

                    evtPage.payloadOffset to this.buffer.getEvtOperationsBuffers().map {
                        it.parseEvtOperation(0)
                    }
                }
            )
        }
    }
}
