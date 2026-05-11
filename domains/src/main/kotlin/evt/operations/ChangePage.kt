package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

// 0 = Forward, 1 = Back, 2 = Specific
enum class ChangePageType(val value: UByte) {
    FORWARD(0u),
    BACK(1u),
    SPECIFIC(2u);

    companion object {
        private val mapping = entries.associateBy { it.value }
        fun from(byte: UByte): ChangePageType = mapping[byte]!!
    }
}
data class ChangePage(
    // opID = 145
    override val opId: UShort = EvtOpIds.CHANGE_PAGE.value,
    override val opSize: UShort = 8u,
    // 0x000 -> 0x3FF = other,
    // 0xFFFF = self
    val target: UShort,
    val changeType: ChangePageType,
    // ID to use when changeType == 2 (0 - 15)
    val changeSpecificId: UByte,
    override val bytes: List<Byte> = emptyList()
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer) = ChangePage(
            target = buffer.getUShort(),
            changeType = ChangePageType.from(buffer.getUByte()),
            changeSpecificId = buffer.getUByte(),
            bytes = buffer.array().toList()
        )
    }
}
