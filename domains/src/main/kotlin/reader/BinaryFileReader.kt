package com.jwhi.som.domains.reader

import com.jwhi.som.domains.InvalidEvtException
import com.jwhi.som.domains.evt.CompareType
import com.jwhi.som.domains.evt.ComparisonType
import com.jwhi.som.domains.evt.EvtCondition
import com.jwhi.som.domains.evt.EvtDefinition
import com.jwhi.som.domains.evt.EvtHeader
import com.jwhi.som.domains.evt.EvtOffsets
import com.jwhi.som.domains.evt.EvtPage
import com.jwhi.som.domains.evt.EvtPageOffsets
import com.jwhi.som.domains.evt.TargetType
import com.jwhi.som.domains.evt.TriggerType
import com.jwhi.som.domains.helpers.getByte
import com.jwhi.som.domains.helpers.getFloat
import com.jwhi.som.domains.helpers.getShort
import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUInt
import com.jwhi.som.domains.helpers.getUShort
import java.io.ByteArrayInputStream

class ExampleReader {
    fun binaryFileReader(fileName: String): ByteArray {
        return javaClass.getResourceAsStream(fileName).readAllBytes()
    }
}

fun ByteArray.toMapEvents(): List<EvtDefinition> {
    this.sliceArray(0..3).let {
        if (!it.contentEquals(EvtHeader.BYTE_HEADER.toByteArray())) {
            throw InvalidEvtException("Invalid header. Expected ${EvtHeader.BYTE_HEADER} but was ${it.contentToString()}")
        }
    }

    val initialStart = 0x04
    val events =  (1..251).map {
        val evtStart = initialStart + (it * 0xFC)
        val evtBytes = this.getBytes(evtStart, 0xFC)
        evtBytes.toEvtDefinition()
    }
    return events
}

fun ByteArray.toEvtDefinition(offset: Int = 0): EvtDefinition {
    val nameBytes = this.getBytes(offset + EvtOffsets.NAME.intOffset(), 0x1E)
    val nameInput = ByteArrayInputStream(nameBytes)
    val nameWithNullChars = nameInput.bufferedReader().use { it.readText() }
    val name = nameWithNullChars.trim(0x00.toChar())

    val targetTypeByte = this[offset + EvtOffsets.TARGET_TYPE.intOffset()]
    val targetType = TargetType.from(targetTypeByte)

    val targetId = this.getShort(offset + EvtOffsets.TARGET_ID.intOffset())

    val triggerTypeByte = this.getUByte(offset + EvtOffsets.TRIGGER_TYPE.intOffset())
    val triggerType = TriggerType.from(triggerTypeByte)

    val triggerItem = this.getUByte(offset + EvtOffsets.TRIGGER_ITEM.intOffset())

    val triggerCone = this.getUShort(offset + EvtOffsets.VIEWING_CONE.intOffset())

    val padding = this.getBytes(offset + EvtOffsets.UNKNOWN.intOffset(), 2)

    val triggerRectWE = this.getFloat(offset + EvtOffsets.TRIGGER_RECTANGLE_WEST_EAST.intOffset())

    val triggerRectNS = this.getFloat(offset + EvtOffsets.TRIGGER_RECTANGLE_NORTH_SOUTH.intOffset())

    val triggerRadius = this.getFloat(offset + EvtOffsets.TRIGGER_CIRCLE_RADIUS.intOffset())

    val condition = EvtCondition(
        compareType = CompareType.from(
            this.getUShort(offset + EvtOffsets.CHECK_TYPE.intOffset())
        ),
        compareId = this.getUShort(offset + EvtOffsets.CHECK_TARGET_ID.intOffset()),
        comparedValue = this.getUShort(offset + EvtOffsets.COMPARISON_VALUE.intOffset()),
        comparisonType = ComparisonType.from(
            this.getUShort(offset + EvtOffsets.COMPARISON_TYPE.intOffset())
        )
    )

    val evtPageStart = offset + EvtOffsets.PAGES_START.intOffset()
    val evtPage = EvtPage(
        payloadOffset = this.getUInt(evtPageStart + EvtPageOffsets.DATA_OFFSET.intOffset()),
        startCondition = EvtCondition(
            compareType = CompareType.from(
                this.getUShort(evtPageStart + EvtPageOffsets.CHECK_TYPE.intOffset())
            ),
            compareId = this.getUShort(evtPageStart + EvtPageOffsets.CHECK_TARGET_ID.intOffset()),
            comparedValue = this.getUShort(evtPageStart + EvtPageOffsets.COMPARISON_VALUE.intOffset()),
            comparisonType = ComparisonType.from(
                this.getUShort(evtPageStart + EvtPageOffsets.COMPARISON_VALUE.intOffset())
            )
        )
    )
    return EvtDefinition(
        name = name,
        targetType = targetType,
        targetId = targetId,
        triggerType = triggerType,
        triggerItem = triggerItem,
        triggerCone = triggerCone,
        padding = padding.toList(),
        triggerRectWE = triggerRectWE,
        triggerRectNS = triggerRectNS,
        triggerRadius = triggerRadius,
        condition = condition,
        pages = listOf(evtPage)
    )
}

fun ByteArray.getEvtOperation(offset: UInt) -> EvtOperation {

}

fun ByteArray.getBytes(offsets: Int, size: Int): ByteArray {
    return this.sliceArray(offsets..< offsets + size)
}