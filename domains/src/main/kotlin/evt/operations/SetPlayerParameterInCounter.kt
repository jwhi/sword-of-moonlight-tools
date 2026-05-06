package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer
import kotlin.toUShort

data class SetPlayerParameterInCounter(
    override val opId: UShort = 84u,
    override val opSize: UShort,
    val playerParameter: PlayerParameter,
    val itemId: UByte,
    val targetCounter: UShort,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            opId: UShort,
            opSize: UShort,
            buffer: ByteBuffer
        ): SetPlayerParameterInCounter {
            return SetPlayerParameterInCounter(
                opId = opId,
                opSize = opSize,
                playerParameter = PlayerParameter.from(buffer.getUByte()),
                itemId = buffer.getUByte(),
                targetCounter = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}
