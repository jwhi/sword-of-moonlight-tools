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
