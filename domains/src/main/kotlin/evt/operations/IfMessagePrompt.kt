package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getNullTerminatedString
import java.nio.ByteBuffer

data class IfMessagePrompt(
    // opID = 141 (-115 signed short) (0x8D 0x00), opSize = variable
    override val opId: UShort = EvtOpIds.IF_MESSAGE_PROMPT.value,
    override val opSize: UShort,
    // Length is not defined but is null terminated
    val text: String,
    val option1: String,
    val option2: String,
    override val bytes: List<Byte> = emptyList()
): EvtOperation {

    companion object {
        fun fromByteBuffer(
            opSize: UShort,
            buffer: ByteBuffer
        ): IfMessagePrompt {
            val bytes = buffer.array()
            val message = buffer.getNullTerminatedString()
            val option1 = buffer.getNullTerminatedString()
            val option2 = buffer.getNullTerminatedString()
            return IfMessagePrompt(
                opSize = opSize,
                text = message,
                option1 = option1,
                option2 = option2,
                bytes = bytes.toList()
            )
        }
    }
}
