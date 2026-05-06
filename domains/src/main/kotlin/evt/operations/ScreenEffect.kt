package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUByte
import java.nio.ByteBuffer

enum class ScreenEffectType(val value: UByte) {
    BLACK_FADES_OFF(0x00u),
    BLACK_FADES_ON(0x01u),
    WHITE_FADES_OFF(0x02u),
    WHITE_FADES_ON(0x03u),
    RED(0x04u),
    GREEN(0x05u),
    BLUE(0x06u);

    companion object {
        private val mapping = entries.associateBy(ScreenEffectType::value)
        fun from(value: UByte) = mapping[value]!!
    }
}

data class BeginScreenEffect(
    override val opId: UShort = EvtOpIds.BEGIN_SCREEN_EFFECT.value,
    override val opSize: UShort = 8u,
    val effectType: ScreenEffectType,
    val loop: Boolean,
    val unimplemented: UShort = 0u,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            buffer: ByteBuffer
        ): BeginScreenEffect {
            return BeginScreenEffect(
                effectType = ScreenEffectType.from(buffer.getUByte()),
                loop = buffer.get() == 0x01.toByte(),
                unimplemented = buffer.get().toUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}

data class EndScreenEffect(
    override val opId: UShort = EvtOpIds.END_SCREEN_EFFECT.value,
    override val opSize: UShort = 4u,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            buffer: ByteBuffer
        ): EndScreenEffect {
            return EndScreenEffect(
                bytes = buffer.array().toList()
            )
        }
    }
}
