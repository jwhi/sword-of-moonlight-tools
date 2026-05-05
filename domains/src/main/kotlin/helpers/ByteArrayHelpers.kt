package com.jwhi.som.domains.helpers

import java.nio.ByteBuffer
import java.nio.ByteOrder

fun ByteBuffer.readUntil(terminator: ByteArray): ByteArray {
    val outputBuffer = allocateLittleEndianByteBuffer(this.remaining())
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
    val opId = this.getUShort()
    val opSize = this.getUShort()
    val outputBuffer = allocateLittleEndianByteBuffer(opSize.toInt())
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
    val evtBuffer = evtOperationChunk.asBufferLittleEndian()
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

fun ByteBuffer.getUByte() = this.get().toUByte()
fun ByteBuffer.getUShort() = this.getShort().toUShort()
fun ByteBuffer.getUInt() = this.getInt().toUInt()

fun allocateLittleEndianByteBuffer(size: Int): ByteBuffer = ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN)

fun ByteArray.asBufferLittleEndian(): ByteBuffer = ByteBuffer.wrap(this).order(ByteOrder.LITTLE_ENDIAN)
