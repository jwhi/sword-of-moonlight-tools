package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUByte
import java.nio.ByteBuffer

enum class Ending(val value: UByte) {
    NORMAL_END(0u),
    ENDING_1(1u),
    ENDING_2(2u),
    ENDING_3(3u);

    companion object {
        private val map = entries.associateBy { it.value }
        fun from(value: UByte) = map[value]!!
    }
}

data class EndGame(
    override val opId: UShort = EvtOpIds.END_GAME.value,
    override val opSize: UShort = 8u,
    val ending: Ending,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): EndGame {
            return EndGame(
                ending = Ending.from(buffer.getUByte()),
                bytes = buffer.array().toList()
            )
        }
    }
}
