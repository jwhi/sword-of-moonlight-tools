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
import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUInt
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

class BinaryBufferReader(val buffer: ByteBuffer) {
    constructor(fileName: String) : this(
            BinaryBufferReader::class.java.getResourceAsStream(fileName)
                .readAllBytes()
                .asBufferLittleEndian()
    )

    fun getEvtDefinitions(): List<EvtDefinition> {
        val magicHeader = buffer.getUInt()
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
        val targetType = TargetType.from(buffer.getUByte())
        val targetId = buffer.getUShort()
        val triggerType = TriggerType.from(
            buffer.getUByte()
        )
        val triggerItem = buffer.getUByte()
        val triggerCone = buffer.getUShort()
        val padding = buffer.getUShort()
        val triggerRectWE = buffer.getFloat()
        val triggerRectNS = buffer.getFloat()

        val triggerRadius = buffer.getFloat()

        val condition = EvtCondition(
            compareType = CompareType.from(
                buffer.getUShort()
            ),
            compareId = buffer.getUShort(),
            comparedValue = buffer.getUShort(),
            comparisonType = ComparisonType.from(
                buffer.getUShort()
            )
        )

        val pages = (1..16).map {
            val payloadOffset = buffer.getUInt()
            val compareType = CompareType.from(
                buffer.getUShort()
            )
            val compareId = buffer.getUShort()
            val comparedValue = buffer.getUShort()
            val comparisonType = ComparisonType.from(
                buffer.getUShort()
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
            padding = padding,
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
