package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getBooleanFlags
import com.jwhi.som.domains.helpers.getUByte
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

data class WarpNPC(
    override val opId: UShort = EvtOpIds.WARP_NPC.value,
    override val opSize: UShort = 24u,
    val npcId: UShort,
    val x: UByte,
    val z: UByte,
    val direction: UShort,
    val unimplemented: UShort = 0u,
    val fineX: Float,
    val fineY: Float,
    val fineZ: Float,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): WarpNPC {
            return WarpNPC(
                npcId = buffer.getUShort(),
                x = buffer.getUByte(),
                z = buffer.getUByte(),
                direction = buffer.getUShort(),
                unimplemented = buffer.getUShort(),
                fineX = buffer.getFloat(),
                fineY = buffer.getFloat(),
                fineZ = buffer.getFloat(),
                bytes = buffer.array().toList()
            )
        }
    }
}

data class WarpEnemy(
    override val opId: UShort = EvtOpIds.WARP_ENEMY.value,
    override val opSize: UShort = 24u,
    val enemyId: UShort,
    val x: UByte,
    val z: UByte,
    val direction: UShort,
    val unimplemented: UShort = 0u,
    val fineX: Float,
    val fineY: Float,
    val fineZ: Float,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): WarpEnemy {
            return WarpEnemy(
                enemyId = buffer.getUShort(),
                x = buffer.getUByte(),
                z = buffer.getUByte(),
                direction = buffer.getUShort(),
                unimplemented = buffer.getUShort(),
                fineX = buffer.getFloat(),
                fineY = buffer.getFloat(),
                fineZ = buffer.getFloat(),
                bytes = buffer.array().toList()
            )
        }
    }
}

data class WarpPlayer(
    val x: UByte,
    val z: UByte,
    val direction: UShort = 0u,
    val fineX: Float = 0f,
    val fineY: Float = 0f,
    val fineZ: Float = 0f,
    val useDirection: Boolean,
    val useFineX: Boolean,
    val useFineY: Boolean,
    val useFineZ: Boolean
) {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): WarpPlayer {
            val x = buffer.getUByte()
            val z = buffer.getUByte()
            val direction = buffer.getUShort()
            val fineX = buffer.getFloat()
            val fineY = buffer.getFloat()
            val fineZ = buffer.getFloat()
            val flagsByte = buffer.get()
            val flags = flagsByte.getBooleanFlags()
            return WarpPlayer(
                x = x,
                z = z,
                direction = direction,
                fineX = fineX,
                fineY = fineY,
                fineZ = fineZ,
                useDirection = flags[0],
                useFineX = flags[1],
                useFineY = flags[2],
                useFineZ = flags[3]
            )
        }
    }
}

data class WarpPlayerBasic(
    override val opId: UShort = EvtOpIds.WARP_PLAYER_BASIC.value,
    override val opSize: UShort = 24u,
    val x: UByte,
    val z: UByte,
    val direction: UShort = 0u,
    val fineX: Float = 0f,
    val fineY: Float = 0f,
    val fineZ: Float = 0f,
    val useDirection: Boolean,
    val useFineX: Boolean,
    val useFineY: Boolean,
    val useFineZ: Boolean,
    override val bytes: List<Byte>
): EvtOperation {
    constructor(warpPlayer: WarpPlayer, bytes: List<Byte>) : this(
        x = warpPlayer.x,
        z = warpPlayer.z,
        direction = warpPlayer.direction,
        fineX = warpPlayer.fineX,
        fineY = warpPlayer.fineY,
        fineZ = warpPlayer.fineZ,
        useDirection = warpPlayer.useDirection,
        useFineX = warpPlayer.useFineX,
        useFineY = warpPlayer.useFineY,
        useFineZ = warpPlayer.useFineZ,
        bytes = bytes
    )

    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): WarpPlayerBasic {
            val warpPlayer = WarpPlayer.fromByteBuffer(buffer)
            return WarpPlayerBasic(
                warpPlayer = warpPlayer,
                bytes = buffer.array().toList()
            )
        }
    }
}

enum class WarpScreenEffect(val value: UByte) {
    NONE(0xFFu),
    BLACK_FADES_OFF(0x00u),
    BLACK_FADES_ON(0x01u),
    WHITE_FADES_OFF(0x02u),
    WHITE_FADES_ON(0x03u);

    companion object {
        private val map = entries.associateBy { it.value }
        fun from(value: UByte): WarpScreenEffect = map[value]!!
    }
}

data class WarpPlayerDetailed(
    override val opId: UShort = EvtOpIds.WARP_PLAYER_DETAILED.value,
    override val opSize: UShort = 28u,
    val warpToMapId: UByte,
    val useDefaultStartPoint: Boolean,
    val screenEffectAsLeave: WarpScreenEffect,
    val screenEffectAsEnter: WarpScreenEffect,
    val x: UByte,
    val z: UByte,
    val direction: UShort = 0u,
    val fineX: Float = 0f,
    val fineY: Float = 0f,
    val fineZ: Float = 0f,
    val useDirection: Boolean,
    val useFineX: Boolean,
    val useFineY: Boolean,
    val useFineZ: Boolean,
    override val bytes: List<Byte>
): EvtOperation {
    constructor(
        warpToMapId: UByte,
        useDefaultStartPoint: Boolean,
        screenEffectAsLeave: WarpScreenEffect,
        screenEffectAsEnter: WarpScreenEffect,
        warpPlayer: WarpPlayer,
        bytes: List<Byte>
    ) : this(
        warpToMapId = warpToMapId,
        useDefaultStartPoint = useDefaultStartPoint,
        screenEffectAsLeave = screenEffectAsLeave,
        screenEffectAsEnter = screenEffectAsEnter,
        x = warpPlayer.x,
        z = warpPlayer.z,
        direction = warpPlayer.direction,
        fineX = warpPlayer.fineX,
        fineY = warpPlayer.fineY,
        fineZ = warpPlayer.fineZ,
        useDirection = warpPlayer.useDirection,
        useFineX = warpPlayer.useFineX,
        useFineY = warpPlayer.useFineY,
        useFineZ = warpPlayer.useFineZ,
        bytes = bytes
    )

    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): WarpPlayerDetailed {
            val warpToMapId = buffer.getUByte()
            val useDefaultStartPoint = buffer.get() != 0x00.toByte()
            val screenEffectAsLeave = WarpScreenEffect.from(buffer.getUByte())
            val screenEffectAsEnter = WarpScreenEffect.from(buffer.getUByte())
            val warpPlayer = WarpPlayer.fromByteBuffer(buffer)
            return WarpPlayerDetailed(
                warpToMapId = warpToMapId,
                useDefaultStartPoint = useDefaultStartPoint,
                screenEffectAsLeave = screenEffectAsLeave,
                screenEffectAsEnter = screenEffectAsEnter,
                warpPlayer = warpPlayer,
                bytes = buffer.array().toList()
            )
        }
    }
}
