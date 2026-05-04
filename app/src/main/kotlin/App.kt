package com.jwhi.som.app

import com.jwhi.som.domains.reader.BinaryBufferReader

@OptIn(ExperimentalUnsignedTypes::class)
fun main() {
    val inputFile = "/ExampleProject/DATA/MAP/00.evt"
    val bufferReader = BinaryBufferReader(inputFile)
    val x = bufferReader.readMapEvent()
    println(x)
}
