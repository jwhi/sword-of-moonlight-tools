package com.jwhi.som.domains.helpers

import java.io.ByteArrayInputStream
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.Scanner

fun ByteArray.getBytes(offset: Int, size: Int): ByteArray {
    return this.sliceArray(offset..< offset + size)
}

fun ByteArray.readString(offset: Int, size: Int): String {
    return ByteArrayInputStream(this.getBytes(offset, size))
        .bufferedReader().use { it.readText() }
        .trim(0x00.toChar())
}

fun readNullTerminatedStrings(buffer: ByteBuffer, lineCount: Int = 1): List<String> {
    val reader = ByteArrayInputStream(buffer.array()).buffered(0x01)
    val scanner = Scanner(reader, StandardCharsets.UTF_8)
    scanner.useDelimiter("\u0000")
    val lines = (1..lineCount).map {
        scanner.next()
    }
    scanner.close()
    reader.close()
    return lines
}
