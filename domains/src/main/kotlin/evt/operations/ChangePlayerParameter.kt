package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

data class ChangePlayerParameter(
    override val opId: UShort = EvtOpIds.CHANGE_PLAYER_PARAMETER.value,
    override val opSize: UShort = 12u,
    val playerParameter: PlayerParameter,
    val wayChanged: WayChanged,
    val itemId: UShort,
    val unimplementedBytes: UShort = 0u,
    val value: UShort,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            buffer: ByteBuffer
        ): ChangePlayerParameter {
            return ChangePlayerParameter(
                playerParameter = PlayerParameter.from(buffer.getUByte()),
                wayChanged = WayChanged.from(buffer.getUByte()),
                itemId = buffer.getUShort(),
                unimplementedBytes = buffer.getUShort(),
                value = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}
