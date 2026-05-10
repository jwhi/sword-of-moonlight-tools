package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

data class GenerateRandomCounterValue(
    override val opId: UShort = EvtOpIds.GENERATE_RANDOM_COUNTER_VALUE.value,
    override val opSize: UShort = 12u,
    val maxValueIsCounterId: Boolean,
    val unimplemented: UShort = 0u,
    val maxValue: UShort,
    val counterId: UShort,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): GenerateRandomCounterValue {
            return GenerateRandomCounterValue(
                maxValueIsCounterId = buffer.getUShort() != 0u.toUShort(),
                unimplemented = buffer.getUShort(),
                maxValue = buffer.getUShort(),
                counterId = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}
