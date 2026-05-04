package com.jwhi.som.domains.helpers

import java.io.ByteArrayInputStream

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

