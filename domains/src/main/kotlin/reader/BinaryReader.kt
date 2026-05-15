package com.jwhi.som.domains.reader

import com.jwhi.som.domains.generated.Evt
import com.jwhi.som.domains.generated.Sys
import io.kaitai.struct.ByteBufferKaitaiStream

object BinaryReader {
    fun getBytes(filename: String): ByteArray {
        return this::class.java.getResourceAsStream(filename)
            .readAllBytes()
    }

    fun getEvt(filename: String): Evt {
        val bytes = getBytes(filename)
        return Evt(ByteBufferKaitaiStream(bytes))
    }

    fun getSys(filename: String): Sys {
        val bytes = getBytes(filename)
        return Sys(ByteBufferKaitaiStream(bytes))
    }
}
