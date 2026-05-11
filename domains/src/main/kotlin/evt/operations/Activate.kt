package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
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

// 65 00 08 00 26 00 01 00
// 65 00 08 00 26 00 00 00
data class DisplayObject(
    override val opId: UShort = EvtOpIds.DISPLAY_OBJECT_ON_OFF.value,
    override val opSize: UShort = 8u,
    val objectId: UShort,
    val show: Boolean,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): DisplayObject {
            return DisplayObject(
                objectId = buffer.getUShort(),
                show = buffer.getUShort() != 0.toUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}
// 64 00 08 00 25 00 00 00
// 64 00 08 00 25 00 01 00
data class ObjectAnimation(
    override val opId: UShort = EvtOpIds.OBJECT_ANIMATION_ON_OFF.value,
    override val opSize: UShort = 8u,
    val objectId: UShort,
    val play: Boolean,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): ObjectAnimation {
            return ObjectAnimation(
                objectId = buffer.getUShort(),
                play = buffer.getUShort() != 0.toUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}