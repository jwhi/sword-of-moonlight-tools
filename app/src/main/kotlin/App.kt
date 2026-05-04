package com.jwhi.som.app

import com.jwhi.som.domains.evt.EvtOpUnimplemented
import com.jwhi.som.domains.helpers.getUShort
import com.jwhi.som.domains.reader.ExampleReader
import com.jwhi.som.domains.reader.toEvtDefinitions
import com.jwhi.som.domains.reader.toMapEvent

@OptIn(ExperimentalUnsignedTypes::class)
fun main() {
    val fileReader = ExampleReader()
    val evt00Bytes = fileReader.binaryFileReader("/ExampleProject/DATA/MAP/00.evt")
    val evt00 = evt00Bytes.toEvtDefinitions()
    val evt00Event = evt00Bytes.toMapEvent()
    println(evt00Event)
}
