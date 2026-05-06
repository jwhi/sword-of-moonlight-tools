package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.helpers.getNullTerminatedString
import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

data class DisplayFormattedMessage(
    // opID = 1, opSize = variable
    override val opId: UShort = 1u,
    override val opSize: UShort,
    // RGBX format (8-bits per component)
    val textColorRed: UByte,
    val textColorGreen: UByte,
    val textColorBlue: UByte,
    val textColorExtraByte: UByte,
    // GDI font weight (0-550 = Normal, 551-999 Bold)
    val fontWeight: UShort,
    val fontWeightExtraBytes: UShort,
    val text: String,
    val fontName: String,
    override val bytes: List<Byte> = emptyList()
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            opId: UShort,
            opSize: UShort,
            buffer: ByteBuffer
        ): DisplayFormattedMessage {

            return DisplayFormattedMessage(
                opId = opId,
                opSize = opSize,
                textColorRed = buffer.getUByte(),
                textColorGreen = buffer.getUByte(),
                textColorBlue = buffer.getUByte(),
                textColorExtraByte = buffer.getUByte(),
                fontWeight = buffer.getUShort(),
                fontWeightExtraBytes = buffer.getUShort(),
                text = buffer.getNullTerminatedString(),
                fontName = buffer.getNullTerminatedString(),
                bytes = buffer.array().toList()
            )
        }
    }
}
