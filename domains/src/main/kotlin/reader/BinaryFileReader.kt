package com.jwhi.som.domains.reader

import com.jwhi.som.domains.InvalidEvtException
import com.jwhi.som.domains.evt.EvtDefinition
import com.jwhi.som.domains.evt.EvtHeader
import com.jwhi.som.domains.evt.TargetType
import com.jwhi.som.domains.evt.TriggerType
import com.jwhi.som.domains.helpers.getFloat
import com.jwhi.som.domains.helpers.getUByte
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
    val nameStart = 4
    val nameEnd = nameStart + 30
    val nameBytes = this.sliceArray(nameStart..nameEnd)
    val nameInput = ByteArrayInputStream(nameBytes)
    val nameWithNullChars = nameInput.bufferedReader().use { it.readText() }
    val name = nameWithNullChars.trim(0x00.toChar())
    println(name)

    val targetTypeByteLocation = nameEnd + 1
    val targetTypeByte = this.get(targetTypeByteLocation)
    val targetType = TargetType.from(targetTypeByte)
    println(targetType)

    val targetIdLocation = targetTypeByteLocation + 1
    val targetId = this.getUShort(targetIdLocation)
    println(targetId)

    val triggerTypeByteLocation = targetIdLocation + 2
    val triggerTypeByte = this.getUByte(triggerTypeByteLocation)
    val triggerType = TriggerType.from(triggerTypeByte)
    println(triggerType)

    val triggerItemByteLocation = triggerTypeByteLocation + 1
    val triggerItemByte = this.getUByte(triggerItemByteLocation)
    println(triggerItemByte)

    val triggerConeByteLocation = triggerItemByteLocation + 1
    val triggerCone = this.getUShort(triggerConeByteLocation)
    println(triggerCone)

    // u16 u16x26; Padding ?
    val triggerRectWEByteLocation = triggerConeByteLocation + 1 + (2 * 26) + 1
    val triggerRectWEBytes = this.getFloat(triggerRectWEByteLocation)
    println(triggerRectWEBytes)





    return events
}
