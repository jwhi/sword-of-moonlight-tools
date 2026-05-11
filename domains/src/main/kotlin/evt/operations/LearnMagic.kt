package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

// 52 00 08 00 00 00 00 00
// 52 00 08 00 01 00 00 00
data class LearnMagic(
    override val opId: UShort = EvtOpIds.LEARN_MAGIC.value,
    override val opSize: UShort = 8u,
    // 1 - 32
    val magicTableId: UShort,
    val unimplemented: UShort = 0u,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): LearnMagic {
            return LearnMagic(
                magicTableId = buffer.getUShort(),
                unimplemented = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}