package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

data class ChangeCounter(
    // opID = 144,
    // 0x90 0x00
    override val opId: UShort = 144u,
    override val opSize: UShort = 12u,
    val counterId: UShort,
    val value: UShort,
    // When 0 = use exact value,
    // when 1 = use value from sourceCounter
    val valueIsCounterId: Boolean,
    val wayChanged: WayChanged,
    val unimplemented: UShort = 0u,
    override val bytes: List<Byte> = emptyList()
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): ChangeCounter {
            return ChangeCounter(
                counterId = buffer.getUShort(),
                value = buffer.getUShort(),
                valueIsCounterId = buffer.get() == 0x01.toByte(),
                wayChanged = WayChanged.from(buffer.getUByte()),
                unimplemented = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}
