package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

data class SetPlayerParameterInCounter(
    override val opId: UShort = EvtOpIds.SET_PLAYER_PARAMETER_IN_COUNTER.value,
    override val opSize: UShort = 8u,
    val playerParameter: PlayerParameter,
    val itemId: UByte,
    val targetCounter: UShort,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): SetPlayerParameterInCounter {
            return SetPlayerParameterInCounter(
                playerParameter = PlayerParameter.from(buffer.getUByte()),
                itemId = buffer.getUByte(),
                targetCounter = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}
