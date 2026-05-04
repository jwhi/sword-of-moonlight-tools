package com.jwhi.som.domains.reader

import com.jwhi.som.domains.InvalidEvtException
import com.jwhi.som.domains.custom.MapEvent
import com.jwhi.som.domains.evt.CompareType
import com.jwhi.som.domains.evt.ComparisonType
import com.jwhi.som.domains.evt.EvtCondition
import com.jwhi.som.domains.evt.EvtDefinition
import com.jwhi.som.domains.evt.EvtOpDisplayMessage
import com.jwhi.som.domains.evt.EvtOpIfMessage
import com.jwhi.som.domains.evt.EvtOpUnimplemented
import com.jwhi.som.domains.evt.EvtOperation
import com.jwhi.som.domains.evt.EvtPage
import com.jwhi.som.domains.evt.TargetType
import com.jwhi.som.domains.evt.TriggerType
import com.jwhi.som.domains.helpers.getEvtOperation
import com.jwhi.som.domains.helpers.readString
import java.nio.ByteBuffer
import java.nio.ByteOrder

class BinaryBufferReader(val buffer: ByteBuffer) {
    constructor(fileName: String) : this(
        ByteBuffer.wrap(
            BinaryBufferReader::class.java.getResourceAsStream(fileName).readAllBytes()
        ).order(ByteOrder.LITTLE_ENDIAN)
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

    fun getEvtOperation(offset: Int): EvtOperation {
        buffer.position(offset)
        val opId = buffer.getShort().toUShort()
        val opSize = buffer.getShort().toUShort()
        val bytes = buffer.getEvtOperation()
        val pageBuffer = ByteBuffer.wrap(bytes)

        return when(opId) {
            0.toUShort() -> EvtOpDisplayMessage(
                opId = opId,
                opSize = opSize,
                text = bytes.readString(0x00, opSize.toInt() - 0x04),
                bytes = bytes.toList()
            )
            141.toUShort() -> EvtOpIfMessage(
                opId = opId,
                opSize = opSize,
                bytes = pageBuffer
            )
            else -> EvtOpUnimplemented(
                opId = opId,
                opSize = opSize,
                bytes = bytes.toList()
            )
        }
    }

    fun readMapEvent(): List<MapEvent> {
        val evtDefinitions = getEvtDefinitions()
        return evtDefinitions.map { definition ->
            MapEvent(
                definition = definition,
                pageOperations = definition.pages.associate { it.payloadOffset to this.getEvtOperation(it.payloadOffset.toInt()) }
            )
        }
    }
}
