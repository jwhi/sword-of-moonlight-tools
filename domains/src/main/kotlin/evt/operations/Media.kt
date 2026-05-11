package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getNullTerminatedString
import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

enum class ImageDisplayOptions(val value: UByte) {
    KEEP_ORIGINAL_RATIO(0u),
    FULL_SCREEN_KEEP_RATIO(1u),
    FULL_SCREEN_STRETCH(2u);

    companion object {
        private val map = entries.associateBy { it.value }
        fun from(value: UByte) = map[value]!!
    }
}

// 2A 00 24 00 01 FF 00 00 45 6C 66 20 61 6E 64 20 43 68 61 69 72 20 46 6F 72 65 76 65 72 2E 62 6D 70 00 00 00
data class DisplayBMP(
    override val opId: UShort = EvtOpIds.DISPLAY_BMP.value,
    override val opSize: UShort,
    val displayOption: ImageDisplayOptions,
    val durationSeconds: UByte,
    val waitForKeyPress: Boolean,
    val unimplemented: UShort = 0u,
    val bmpFilename: String,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(opSize: UShort, buffer: ByteBuffer): DisplayBMP {
            val displayOption = ImageDisplayOptions.from(buffer.getUByte())
            val durationSeconds = buffer.getUByte()
            val unimplemented = buffer.getUShort()
            val bmpFilename = buffer.getNullTerminatedString()
            return DisplayBMP(
                opSize = opSize,
                displayOption = displayOption,
                durationSeconds = durationSeconds,
                waitForKeyPress = durationSeconds == UByte.MAX_VALUE,
                unimplemented = unimplemented,
                bmpFilename = bmpFilename,
                bytes = buffer.array().toList()
            )
        }
    }
}

data class DisplayMovie(
    // 2B 00 20 00 77 61 6C 6B 69 6E 67 2D 61 72 6F 75 6E 64 2D 61 67 61 69 6E 2E 61 76 69 00 00 00 00
    override val opId: UShort = EvtOpIds.DISPLAY_MOVIE.value,
    override val opSize: UShort,
    val movieFilename: String,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(opSize: UShort, buffer: ByteBuffer): DisplayMovie {
            return DisplayMovie(
                opSize = opSize,
                movieFilename = buffer.getNullTerminatedString(),
                bytes = buffer.array().toList()
            )
        }
    }
}

// 2C 00 08 00 00 00 00 00
// 2C 00 08 00 00 00 00 00
// 2C 00 08 00 02 00 00 00
// 2C 00 08 00 00 00 00 00
// 2C 00 08 00 01 00 00 00
data class PlaySoundEffect(
    override val opId: UShort = EvtOpIds.PLAY_SOUND_EFFECT.value,
    override val opSize: UShort = 8u,
    val soundEffectId: UShort,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): PlaySoundEffect {
            return PlaySoundEffect(
                soundEffectId = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}

// 2D 00 0C 00 00 00 00 00 00 00 00 00
// 2D 00 0C 00 01 00 00 00 00 00 00 00
// 2D 00 1C 00 01 00 00 00 65 78 61 6D 70 6C 65 2D 65 78 70 6C 6F 72 65 2E 77 61 76 00
// 2D 00 20 00 00 00 00 00 65 78 61 6D 70 6C 65 2D 61 6D 62 69 65 6E 63 65 2E 77 61 76 00 00 00 00
// 2D 00 1C 00 01 00 00 00 65 78 61 6D 70 6C 65 2D 66 69 67 68 74 2E 77 61 76 00 00 00
data class ChangeBGM(
    override val opId: UShort = EvtOpIds.CHANGE_BGM.value,
    override val opSize: UShort,
    val loop: Boolean,
    val unimplemented: UShort = 0u,
    val bgmFilename: String,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(opSize: UShort, buffer: ByteBuffer): ChangeBGM {
            return ChangeBGM(
                opSize = opSize,
                loop = buffer.getUShort() != 0u.toUShort(),
                unimplemented = buffer.getUShort(),
                bgmFilename = buffer.getNullTerminatedString(),
                bytes = buffer.array().toList()
            )
        }
    }
}

// 2E 00 08 00 00 00 00 00
// 2E 00 08 00 01 00 00 00
// 2E 00 08 00 00 00 00 00
data class StopPlayBGM(
    override val opId: UShort = EvtOpIds.BGM_ON_OFF.value,
    override val opSize: UShort = 8u,
    val play: Boolean,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): StopPlayBGM {
            return StopPlayBGM(
                play = buffer.getUByte() != 0u.toUByte(),
                bytes = buffer.array().toList()
            )
        }
    }
}
