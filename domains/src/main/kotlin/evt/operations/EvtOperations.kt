package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

/**
 * EVT Operations
 * EVT operations can either be present in a file or not,
 * and in any order.
 *
 * These are what is stored at the payloadOffset within an EVT_PAGE.
 *
 */
interface EvtOperation {
    // Operation ID. See operation definitions below for valid/known values.
    val opId: UShort
    // Size of the operation (including the EVT_OPCODE.)
    val opSize: UShort
    // Debug to view and validate bytes
    val bytes: List<Byte>
}

data class UnimplementedOperation(
    override val opId: UShort,
    override val opSize: UShort,
    override val bytes: List<Byte>
): EvtOperation

fun ByteBuffer.parseEvtOperation(offset: Int): EvtOperation {
    this.position(offset)
    val opId = this.getUShort()
    val opSize = this.getUShort()
    val bytes = this.array()
    val pageBuffer = this

    return when(opId) {
        EvtOpIds.DISPLAY_MESSAGE.value -> DisplayMessage.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.DISPLAY_FORMATTED_MESSAGE.value -> DisplayFormattedMessage.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.SHOP_OPEN.value -> ShopOpen.fromByteBuffer(pageBuffer)
        EvtOpIds.WARP_NPC.value -> WarpNPC.fromByteBuffer(pageBuffer)
        EvtOpIds.WARP_ENEMY.value -> WarpEnemy.fromByteBuffer(pageBuffer)
        EvtOpIds.ACTIVATE_NPC.value -> ActivateNPC.fromByteBuffer(pageBuffer)
        EvtOpIds.ACTIVATE_ENEMY.value -> ActivateEnemy.fromByteBuffer(pageBuffer)
        EvtOpIds.ACTIVATE_ITEM.value -> ActivateItem.fromByteBuffer(pageBuffer)
        EvtOpIds.BEGIN_SCREEN_EFFECT.value -> BeginScreenEffect.fromByteBuffer(pageBuffer)
        EvtOpIds.END_SCREEN_EFFECT.value -> EndScreenEffect.fromByteBuffer(pageBuffer)
        EvtOpIds.WARP_PLAYER_DETAILED.value -> WarpPlayerDetailed.fromByteBuffer(pageBuffer)
        EvtOpIds.WARP_PLAYER_BASIC.value -> WarpPlayerBasic.fromByteBuffer(pageBuffer)
        EvtOpIds.CHANGE_PLAYER_PARAMETER.value -> ChangePlayerParameter.fromByteBuffer(pageBuffer)
        EvtOpIds.SET_PLAYER_PARAMETER_IN_COUNTER.value -> SetPlayerParameterInCounter.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.SAVE_POINT.value -> SavePoint(bytes = pageBuffer.array().toList())
        EvtOpIds.IF_COUNTER_CONDITION.value -> IfCounterCondition.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.IF_MESSAGE_PROMPT.value -> IfMessagePrompt.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.OTHERWISE.value -> Otherwise(bytes = pageBuffer.array().toList())
        EvtOpIds.END_IF.value -> EndIf(bytes = pageBuffer.array().toList())
        EvtOpIds.CHANGE_COUNTER.value -> ChangeCounter.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.CHANGE_PAGE.value -> ChangePage.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.END.value -> OperationEnd(bytes = pageBuffer.array().toList())
        else -> UnimplementedOperation(
            opId = opId,
            opSize = opSize,
            bytes = bytes.toList()
        )
    }
}
