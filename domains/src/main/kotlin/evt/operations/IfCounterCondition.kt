package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

enum class CompareType(val value: UByte) {
    EQUALS(0u),
    NOT_EQUALS(1u),
    GREATER_THAN(2u),
    LESS_THAN(3u),
    NONE(0xFFu);

    companion object {
        private val mapping = entries.associateBy(CompareType::value)
        fun from(value: UByte) = mapping[value] ?: NONE // Or default to something
    }
}

data class IfCounterCondition(
    override val opId: UShort = 140u,
    override val opSize: UShort,
    val counterId: UShort,
    val value: UShort,
    val valueIsCounterId: Boolean,
    val compareType: CompareType,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            opId: UShort,
            opSize: UShort,
            buffer: ByteBuffer
        ): IfCounterCondition {
            return IfCounterCondition(
                opId = opId,
                opSize = opSize,
                counterId = buffer.getUShort(),
                value = buffer.getUShort(),
                valueIsCounterId = buffer.get() == 0x01.toByte(),
                compareType = CompareType.from(buffer.getUByte()),
                bytes = buffer.array().toList()
            )
        }
    }
}
