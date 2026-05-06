package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.helpers.getNullTerminatedString
import java.nio.ByteBuffer

data class DisplayMessage(
    // opID = 0, opSize = variable
    override val opId: UShort = 0u,
    override val opSize: UShort,
    // Length is opSize - 4 bytes (id and size bytes are included in op size)
    val text: String,
    override val bytes: List<Byte> = emptyList()
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            opId: UShort,
            opSize: UShort,
            buffer: ByteBuffer
        ): DisplayMessage {
            return DisplayMessage(
                opId = opId,
                opSize = opSize,
                text = buffer.getNullTerminatedString(),
                bytes = buffer.array().toList()
            )
        }
    }
}
