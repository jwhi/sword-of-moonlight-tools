package com.jwhi.som.app

import com.jwhi.som.domains.reader.ExampleReader
import com.jwhi.som.domains.reader.toMapEvents

@OptIn(ExperimentalUnsignedTypes::class)
fun main() {
    val fileReader = ExampleReader()
    val evt00Bytes = fileReader.binaryFileReader("/ExampleProject/DATA/MAP/00.evt")
    val evt00 = evt00Bytes.toMapEvents()
    println(evt00)
}
