package com.jwhi.som.domains.reader

import com.jwhi.som.domains.InvalidEvtException
import com.jwhi.som.domains.evt.CompareType
import com.jwhi.som.domains.evt.ComparisonType
import com.jwhi.som.domains.evt.EvtCondition
import com.jwhi.som.domains.evt.EvtDefinition
import com.jwhi.som.domains.evt.EvtHeader
import com.jwhi.som.domains.evt.EvtPage
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

fun ByteArray.toMapEvents(): List<EvtDefinition> {
    val events = mutableListOf<EvtDefinition>()
    this.sliceArray(0..3).let {
        if (!it.contentEquals(EvtHeader.BYTE_HEADER.toByteArray())) {
            throw InvalidEvtException("Invalid header. Expected ${EvtHeader.BYTE_HEADER} but was ${it.contentToString()}")
        }
    }

    // EVT Definition
    // val systemEvent = this.toEvtDefinition(4)
    // val firstEvent = this.toEvtDefinition(0x9DC)
    // val manAfterHelpingDwarf = this.toEvtDefinition(0xAD8)
    // val manBeforeDwarf = this.toEvtDefinition(0xBD4)
    // val banditEvent = this.toEvtDefinition(0xCD0)
    // val dwarfTalking = this.toEvtDefinition(0xDCC)
    val rectTest1 = this.toEvtDefinition(0xEC8)
    val circEvent1 = this.toEvtDefinition(0xFC4)
    /*
    listOf(
        systemEvent,
        firstEvent,
        manAfterHelpingDwarf,
        manBeforeDwarf,
        banditEvent,
        dwarfTalking,
        rectTest1,
        circEvent1,
    )
     */
    return listOf(
        rectTest1,
        circEvent1
    )
}

fun ByteArray.toEvtDefinition(offset: Int): EvtDefinition {
    val nameEnd = offset + 30
    val nameBytes = this.sliceArray(offset..nameEnd)
    val nameInput = ByteArrayInputStream(nameBytes)
    val nameWithNullChars = nameInput.bufferedReader().use { it.readText() }
    val name = nameWithNullChars.trim(0x00.toChar())

    val targetTypeByteLocation = nameEnd + 1
    val targetTypeByte = this.get(targetTypeByteLocation)
    val targetType = TargetType.from(targetTypeByte)

    val targetIdLocation = targetTypeByteLocation + 1
    val targetId = this.getShort(targetIdLocation)

    val triggerTypeByteLocation = targetIdLocation + 2
    val triggerTypeByte = this.getUByte(triggerTypeByteLocation)
    val triggerType = TriggerType.from(triggerTypeByte)

    val triggerItemByteLocation = triggerTypeByteLocation + 1
    val triggerItem = this.getUByte(triggerItemByteLocation)

    val triggerConeByteLocation = triggerItemByteLocation + 1
    val triggerCone = this.getUShort(triggerConeByteLocation)

    // u16 u16x26; Padding ?
    val paddingLocationStart = triggerConeByteLocation + 1
    val paddingLocationEnd = paddingLocationStart + 2
    val padding = this.slice(paddingLocationStart..paddingLocationEnd).map { it.toUByte() }

    // 1045 TOO LARGE
    // RECT FLOAT: 3821 (EF0) IS ACTUAL
    val triggerRectWEByteLocation = paddingLocationEnd + 1
    val triggerRectWEBytes = this.sliceArray(triggerRectWEByteLocation..triggerRectWEByteLocation + 4)
    println(triggerRectWEBytes.decodeToString())
    val triggerRectWE = this.getFloat(triggerRectWEByteLocation)


    val triggerRectNSByteLocation = triggerRectWEByteLocation + 4
    val triggerRectNSBytes = this.sliceArray(triggerItemByteLocation.. triggerItemByteLocation + 4)
    println(triggerRectNSBytes.decodeToString())
    val triggerRectNS = this.getFloat(triggerRectNSByteLocation)

    val triggerRadiusByteLocation = triggerRectNSByteLocation + 4
    val triggerRadiusBytes = this.sliceArray(triggerRadiusByteLocation..triggerRadiusByteLocation + 4)
    println(triggerRadiusBytes.decodeToString())
    val triggerRadius = this.getFloat(triggerRadiusByteLocation)

    val evtConditionStart = triggerRadiusByteLocation + 3
    val condition = EvtCondition(
        compareType = CompareType.from(
            this.getUShort(evtConditionStart)
        ),
        compareId = this.getUShort(evtConditionStart + 2),
        comparedValue = this.getUShort(evtConditionStart + 4),
        comparisonType = ComparisonType.from(
            this.getUShort(evtConditionStart + 6)
        )
    )

    val evtPageStart = evtConditionStart + 8
    val evtPage = EvtPage(
        payloadOffset = this.getUInt(evtPageStart),
        startCondition = EvtCondition(
            compareType = CompareType.from(
                this.getUShort(evtPageStart + 4)
            ),
            compareId = this.getUShort(evtPageStart + 6),
            comparedValue = this.getUShort(evtPageStart + 8),
            comparisonType = ComparisonType.from(
                this.getUShort(evtPageStart + 10)
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
        padding = padding,
        triggerRectWE = triggerRectWE,
        triggerRectNS = triggerRectNS,
        triggerRadius = triggerRadius,
        condition = condition,
        pages = listOf(evtPage)
    )
}