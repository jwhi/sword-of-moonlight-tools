package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

data class ActivateNPC(
    override val opId: UShort = EvtOpIds.ACTIVATE_NPC.value,
    override val opSize: UShort = 8u,
    val npcId: UShort,
    override val bytes: List<Byte> = listOf()
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): ActivateNPC {
            return ActivateNPC(
                npcId = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}

data class ActivateEnemy(
    override val opId: UShort = EvtOpIds.ACTIVATE_ENEMY.value,
    override val opSize: UShort = 8u,
    val enemyId: UShort,
    override val bytes: List<Byte> = listOf()
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): ActivateEnemy {
            return ActivateEnemy(
                enemyId = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}

data class ActivateItem(
    override val opId: UShort = EvtOpIds.ACTIVATE_ITEM.value,
    override val opSize: UShort = 8u,
    val itemId: UShort,
    override val bytes: List<Byte> = listOf()
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): ActivateItem {
            return ActivateItem(
                itemId = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}
