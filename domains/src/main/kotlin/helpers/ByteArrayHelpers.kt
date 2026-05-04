package com.jwhi.som.domains.helpers

import java.io.ByteArrayInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun ByteArray.getBytes(offset: Int, size: Int): ByteArray {
    return this.sliceArray(offset..< offset + size)
}

fun ByteArray.readString(offset: Int, size: Int): String {
    return ByteArrayInputStream(this.getBytes(offset, size))
        .bufferedReader().use { it.readText() }
        .trim(0x00.toChar())
}

fun ByteBuffer.readUntil(terminator: ByteArray): ByteArray {
    val outputBuffer = ByteBuffer.allocate(this.remaining()).order(ByteOrder.LITTLE_ENDIAN)
    while (this.remaining() > 0) {
        outputBuffer.put(this.get())
        if (outputBuffer.position() >= terminator.size) {
            val outputBufferArray = outputBuffer.array().slice(
                outputBuffer.position() - terminator.size ..<outputBuffer.position()
            ).toByteArray()

            if (outputBufferArray.contentEquals(terminator)) {
                break
            }
        }
    }
    return outputBuffer.array().slice(0..< outputBuffer.position()).toByteArray()
}


/*
 * Get bytes related to EVT Operations that end in 0xFF 0x04
 */
fun ByteBuffer.getEvtOperation(): ByteArray {
    val evtBytes = this.readUntil(byteArrayOf(0xFF.toByte(), 0x04))
    return evtBytes
}

fun readNullTerminatedStrings(buffer: ByteBuffer, lineCount: Int = 1): List<String> {
    val lines = (1..lineCount).map {
        buffer.readUntil(byteArrayOf(0x00)).decodeToString().dropLast(1)
    }
    return lines
}

fun ByteBuffer.getNullTerminatedString() = this.readUntil(byteArrayOf(0x00))
        .decodeToString()
        .dropLast(1)
