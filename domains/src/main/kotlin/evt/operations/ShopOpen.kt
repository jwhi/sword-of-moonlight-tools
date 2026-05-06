package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

data class ShopOpen(
    override val opId: UShort = EvtOpIds.SHOP_OPEN.value,
    override val opSize: UShort = 8u,
    val shopId: UShort,
    val additionalBytes: UShort = 0u,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): ShopOpen {
            return ShopOpen(
                shopId = buffer.getUShort(),
                additionalBytes = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}
