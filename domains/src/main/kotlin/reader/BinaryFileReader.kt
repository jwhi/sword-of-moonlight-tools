package com.jwhi.som.domains.reader

import com.jwhi.som.domains.InvalidEvtException
import com.jwhi.som.domains.evt.EvtDefinition
import com.jwhi.som.domains.evt.EvtHeader

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
    return events
}
