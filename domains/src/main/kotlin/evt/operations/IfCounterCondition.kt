package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
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
        private val mapping = entries.associateBy { it.value }
        fun from(value: UByte) = mapping[value] ?: NONE
    }
}

data class IfCounterCondition(
    override val opId: UShort = EvtOpIds.IF_COUNTER_CONDITION.value,
    override val opSize: UShort = 12u,
    val counterId: UShort,
    val value: UShort,
    val valueIsCounterId: Boolean,
    val compareType: CompareType,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): IfCounterCondition {
            return IfCounterCondition(
                counterId = buffer.getUShort(),
                value = buffer.getUShort(),
                valueIsCounterId = buffer.get() == 0x01.toByte(),
                compareType = CompareType.from(buffer.getUByte()),
                bytes = buffer.array().toList()
            )
        }
    }
}
