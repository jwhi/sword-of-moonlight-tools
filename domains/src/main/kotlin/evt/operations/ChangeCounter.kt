package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

enum class CounterMode(val value: UByte) {
    SET_TO(0u),
    INCREMENT_BY(1u),
    DECREMENT_BY(2u),
    COUNTER(3u);

    companion object {
        private val mapping = entries.associateBy(CounterMode::value)
        fun from(byte: UByte): CounterMode = mapping[byte]!!
    }
}

data class ChangeCounter(
    // opID = 144,
    // 0x90 0x00
    override val opId: UShort = 144u,
    override val opSize: UShort = 12u,
    val counterId: UShort,
    val exactValue: UShort,
    // When 0 = use exact value,
    // when 1 = use value from sourceCounter
    val useTarget: Boolean,
    val mode: CounterMode,
    val sourceCounter: UShort,
    override val bytes: List<Byte> = emptyList()
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            opId: UShort,
            opSize: UShort,
            buffer: ByteBuffer
        ): ChangeCounter {
            return ChangeCounter(
                opId = opId,
                opSize = opSize,
                counterId = buffer.getUShort(),
                exactValue = buffer.getUShort(),
                useTarget = buffer.get() == 0x01.toByte(),
                mode = CounterMode.from(buffer.getUByte()),
                sourceCounter = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}
