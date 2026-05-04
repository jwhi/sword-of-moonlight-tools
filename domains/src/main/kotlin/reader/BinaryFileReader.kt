package com.jwhi.som.domains.reader

import com.jwhi.som.domains.InvalidEvtException
import com.jwhi.som.domains.custom.MapEvent
import com.jwhi.som.domains.evt.CompareType
import com.jwhi.som.domains.evt.ComparisonType
import com.jwhi.som.domains.evt.EvtCondition
import com.jwhi.som.domains.evt.EvtDefinition
import com.jwhi.som.domains.evt.EvtHeader
import com.jwhi.som.domains.evt.EvtOffsets
import com.jwhi.som.domains.evt.EvtOpDisplayMessage
import com.jwhi.som.domains.evt.EvtOpIfMessage
import com.jwhi.som.domains.evt.EvtOperation
import com.jwhi.som.domains.evt.EvtOpUnimplemented
import com.jwhi.som.domains.evt.EvtPage
import com.jwhi.som.domains.evt.EvtPageDataOffsets
import com.jwhi.som.domains.evt.EvtPageOffsets
import com.jwhi.som.domains.evt.TargetType
import com.jwhi.som.domains.evt.TriggerType
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

fun ByteArray.toMapEvent(): List<MapEvent> {
    val evtDefinitions = this.toEvtDefinitions()
    return evtDefinitions.map {
        MapEvent(
            definition = it,
            pageOperations = it.pages.associate { it.payloadOffset to this.getEvtOperation(it.payloadOffset) }
        )
    }
}

fun ByteArray.toEvtDefinitions(): List<EvtDefinition> {
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
    val name = this.readString(offset + EvtOffsets.NAME.intOffset(), 0x1E)

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
    val evtPagesEnd = offset + 0xFBu.toInt()
    val pagesBytes = this.slice(evtPageStart..evtPagesEnd)
    val evtPages = pagesBytes.chunked(12).map {
        val pageBytes = it.toByteArray()
        EvtPage(
            payloadOffset = pageBytes.getUInt(EvtPageOffsets.DATA_OFFSET.intOffset()),
            startCondition = EvtCondition(
                compareType = CompareType.from(
                    pageBytes.getUShort(EvtPageOffsets.CHECK_TYPE.intOffset())
                ),
                compareId = pageBytes.getUShort(EvtPageOffsets.CHECK_TARGET_ID.intOffset()),
                comparedValue = pageBytes.getUShort(EvtPageOffsets.COMPARISON_VALUE.intOffset()),
                comparisonType = ComparisonType.from(
                    pageBytes.getUShort(EvtPageOffsets.COMPARISON_TYPE.intOffset())
                )
            )
        )
    }.filter {
        it.payloadOffset != 0u
    }

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
        pages = evtPages
    )
}

fun ByteArray.getEvtOperation(offset: UInt): EvtOperation {
    val opId = this.getUShort(offset.toInt() + EvtPageDataOffsets.OPERATION_TYPE.byte)
    val opSize = this.getUShort(offset.toInt() + EvtPageDataOffsets.OPERATION_SIZE.byte)
    val bytes = this.getBytes(offset.toInt() + EvtPageDataOffsets.OPERATION_TYPE.intOffset(), opSize.toInt())
    return when(opId) {
        0.toUShort() -> EvtOpDisplayMessage(
            opId = opId,
            opSize = opSize,
            text = bytes.readString(0x04, opSize.toInt() - 0x04),
            bytes = bytes.toList()
        )
        141.toUShort() -> EvtOpIfMessage(
            opId = opId,
            opSize = opSize,
            bytes = bytes
        )
        else -> EvtOpUnimplemented(
            opId = opId,
            opSize = opSize,
            bytes = bytes.toList()
        )
    }
}

fun ByteArray.getBytes(offset: Int, size: Int): ByteArray {
    return this.sliceArray(offset..< offset + size)
}

fun ByteArray.readString(offset: Int, size: Int): String {
    return ByteArrayInputStream(this.getBytes(offset, size))
        .bufferedReader().use { it.readText() }
        .trim(0x00.toChar())
}

fun ByteArray.readStringToTerminator(offset: Int, terminator: Byte = 0x00): String {
    val terminatorLocation = findTerminator(offset, terminator)
    return this.readString(offset, terminatorLocation - offset)
}

fun ByteArray.findTerminator(offset: Int, terminator: Byte = 0x00): Int {
    return this.slice(offset..< this.size).indexOfFirst { it == terminator } + offset
}