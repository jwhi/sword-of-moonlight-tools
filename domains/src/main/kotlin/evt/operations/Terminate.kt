package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

data class TerminateNPC(
    override val opId: UShort = EvtOpIds.TERMINATE_NPC.value,
    override val opSize: UShort = 8u,
    val npcId: UShort,
    override val bytes: List<Byte> = listOf()
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): TerminateNPC {
            return TerminateNPC(
                npcId = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}

data class TerminateEnemy(
    override val opId: UShort = EvtOpIds.TERMINATE_ENEMY.value,
    override val opSize: UShort = 8u,
    val enemyId: UShort,
    override val bytes: List<Byte> = listOf()
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): TerminateEnemy {
            return TerminateEnemy(
                enemyId = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}
