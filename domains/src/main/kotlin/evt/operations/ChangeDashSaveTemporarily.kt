package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

enum class ChangeTarget(val value: UByte) {
    DASH(0x00u),
    SAVE(0x01u);

    companion object {
        private val map = entries.associateBy { it.value }
        fun from(value: UByte) = map[value]!!
    }
}

data class ChangeDashSaveTemporarily(
    override val opId: UShort = EvtOpIds.CHANGE_DASH_SAVE_TEMPORARILY.value,
    override val opSize: UShort = 8u,
    val target: ChangeTarget,
    val enabled: Boolean,
    val unimplemented: UShort = 0u,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): ChangeDashSaveTemporarily {
            return ChangeDashSaveTemporarily(
                target = ChangeTarget.from(buffer.getUByte()),
                enabled = buffer.get() != 0.toByte(),
                unimplemented = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}
