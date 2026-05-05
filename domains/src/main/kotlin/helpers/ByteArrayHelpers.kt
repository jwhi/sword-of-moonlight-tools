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

fun ByteBuffer.getEvtOperationsByteBuffer(): ByteBuffer {
    val opId = this.getShort().toUShort()
    val opSize = this.getShort().toUShort()
    val outputBuffer = ByteBuffer.allocate(opSize.toInt()).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.putShort(opId.toShort())
    outputBuffer.putShort(opSize.toShort())
    while (outputBuffer.hasRemaining()) {
        outputBuffer.put(this.get())
    }
    return outputBuffer
}

fun ByteBuffer.getEvtOperationsBuffers(): List<ByteBuffer> {
    val evtOperationChunk = readUntil(
        byteArrayOf(
            0xFF.toByte(),
            0xFF.toByte(),
            0x04.toByte(),
            0x00.toByte()
        )
    )
    val evtBuffer = ByteBuffer.wrap(evtOperationChunk).order(ByteOrder.LITTLE_ENDIAN)
    evtBuffer.position(0)
    val output = mutableListOf<ByteBuffer>()
    while (evtBuffer.remaining() > 0) {
        output.add(evtBuffer.getEvtOperationsByteBuffer())
    }
    return output
}


fun ByteBuffer.getNullTerminatedString() = this.readUntil(byteArrayOf(0x00))
        .decodeToString()
        .dropLast(1)
