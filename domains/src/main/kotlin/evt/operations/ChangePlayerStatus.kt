package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUByte
import java.nio.ByteBuffer

enum class PlayerStatus(val value: UByte) {
    POISON(0u),
    PARALYZE(1u),
    DARK(2u),
    CURSE(3u),
    SLOW(4u);

    companion object {
        private val map = entries.associateBy { it.value }
        fun from(value: UByte) = map[value]!!
    }
}

data class ChangePlayerStatus(
    override val opId: UShort = EvtOpIds.CHANGE_PLAYER_STATUS.value,
    override val opSize: UShort = 8u,
    val playerStatus: PlayerStatus,
    val enabled: Boolean,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): ChangePlayerStatus {
            return ChangePlayerStatus(
                playerStatus = PlayerStatus.from(buffer.getUByte()),
                enabled = buffer.get() != 0.toByte(),
                bytes = buffer.array().toList()
            )
        }
    }
}
