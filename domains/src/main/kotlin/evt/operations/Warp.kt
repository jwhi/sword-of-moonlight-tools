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
    val unimplemented: UShort,
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
/*
28 WarpEne2(x7z69x-.1y-.2z-.5d270
UnimplementedOperation(
opId=26, opSize=24, bytes=[26, 0, 24, 0,
2, 0,
7,
69,
14, 1, 0, 0, -51, -52, -52, -67, -51, -52, 76, -66, 0, 0, 0, -65])

 */
data class WarpEnemy(
    override val opId: UShort = EvtOpIds.WARP_ENEMY.value,
    override val opSize: UShort = 24u,
    val enemyId: UShort,
    val x: UByte,
    val z: UByte,
    val direction: UShort,
    val unimplemented: UShort,
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
    override val opId: UShort = EvtOpIds.WARP_PLAYER.value,
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
                useFineZ = flags[3],
                bytes = buffer.array().toList()
            )
        }
    }
}

/*
3C 00
1C 00
3F 01
FF 03 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00

3C 00 1C 00 3F 00 01 02 5E 04 FB 00 00 00 80 BF 00 00 A0 41 00 00 80 3F 0F 00 00 00

 */
data class WarpPlayerDetailed(
    override val opId: UShort = EvtOpIds.WARP_PLAYER_DETAILED.value,
    override val opSize: UShort = 24u,
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
        fun fromByteBuffer(buffer: ByteBuffer): WarpPlayerDetailed {
            return WarpPlayerDetailed(
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

/*
19 Chair Always On Return WhenLow
WARP PLAYER 21 97
fine tuned
+- x: 0
+- y: 0
+- z: 0
direction: 0-360:0
UnimplementedOperation(opId=61, opSize=24, bytes=[61, 0, 24, 0, 21, 97, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])

WarpNPC2(x6z69x.1y.2z.5d120)
UnimplementedOperation(opId=25, opSize=24, bytes=[25, 0, 24, 0, 2, 0, 6, 69, 120, 0, 0, 0, -51, -52, -52, 61, -51, -52, 76, 62, 0, 0, 0, 63])

27 WarpNPC2(x6z69x.1y.2z.5d120)
UnimplementedOperation(opId=25, opSize=24, bytes=[25, 0, 24, 0, 2, 0, 6, 69, 120, 0, 0, 0, -51, -52, -52, 61, -51, -52, 76, 62, 0, 0, 0, 63])

 */