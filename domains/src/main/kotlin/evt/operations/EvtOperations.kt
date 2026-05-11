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
        EvtOpIds.TERMINATE_NPC.value -> TerminateNPC.fromByteBuffer(pageBuffer)
        EvtOpIds.TERMINATE_ENEMY.value -> TerminateEnemy.fromByteBuffer(pageBuffer)
        EvtOpIds.BEGIN_SCREEN_EFFECT.value -> BeginScreenEffect.fromByteBuffer(pageBuffer)
        EvtOpIds.END_SCREEN_EFFECT.value -> EndScreenEffect.fromByteBuffer(pageBuffer)
        EvtOpIds.DISPLAY_BMP.value -> DisplayBMP.fromByteBuffer(opSize, pageBuffer)
        EvtOpIds.DISPLAY_MOVIE.value -> DisplayMovie.fromByteBuffer(opSize, pageBuffer)
        EvtOpIds.PLAY_SOUND_EFFECT.value -> PlaySoundEffect.fromByteBuffer(pageBuffer)
        EvtOpIds.CHANGE_BGM.value -> ChangeBGM.fromByteBuffer(opSize, pageBuffer)
        EvtOpIds.BGM_ON_OFF.value -> StopPlayBGM.fromByteBuffer(pageBuffer)
        EvtOpIds.WARP_PLAYER_DETAILED.value -> WarpPlayerDetailed.fromByteBuffer(pageBuffer)
        EvtOpIds.WARP_PLAYER_BASIC.value -> WarpPlayerBasic.fromByteBuffer(pageBuffer)
        EvtOpIds.CHANGE_PLAYER_PARAMETER.value -> ChangePlayerParameter.fromByteBuffer(pageBuffer)
        EvtOpIds.LEARN_MAGIC.value -> LearnMagic.fromByteBuffer(pageBuffer)
        EvtOpIds.RECOVER_ALL.value -> RecoverAll(bytes = pageBuffer.array().toList())
        EvtOpIds.SET_PLAYER_PARAMETER_IN_COUNTER.value -> SetPlayerParameterInCounter.fromByteBuffer(pageBuffer)
        EvtOpIds.OBJECT_ANIMATION_ON_OFF.value -> ObjectAnimation.fromByteBuffer(pageBuffer)
        EvtOpIds.DISPLAY_OBJECT_ON_OFF.value -> DisplayObject.fromByteBuffer(pageBuffer)
        EvtOpIds.MOVE_OBJECT.value -> MoveObject.fromByteBuffer(pageBuffer)
        EvtOpIds.CHANGE_DASH_SAVE_TEMPORARILY.value -> ChangeDashSaveTemporarily.fromByteBuffer(pageBuffer)
        EvtOpIds.SAVE_POINT.value -> SavePoint(bytes = pageBuffer.array().toList())
        EvtOpIds.END_GAME.value -> EndGame.fromByteBuffer(pageBuffer)
        EvtOpIds.IF_COUNTER_CONDITION.value -> IfCounterCondition.fromByteBuffer(buffer = pageBuffer)
        EvtOpIds.IF_MESSAGE_PROMPT.value -> IfMessagePrompt.fromByteBuffer(opSize = opSize, buffer = pageBuffer)
        EvtOpIds.OTHERWISE.value -> Otherwise(bytes = pageBuffer.array().toList())
        EvtOpIds.END_IF.value -> EndIf(bytes = pageBuffer.array().toList())
        EvtOpIds.CHANGE_COUNTER.value -> ChangeCounter.fromByteBuffer(buffer = pageBuffer)
        EvtOpIds.CHANGE_PAGE.value -> ChangePage.fromByteBuffer(buffer = pageBuffer)
        EvtOpIds.GENERATE_RANDOM_COUNTER_VALUE.value -> GenerateRandomCounterValue.fromByteBuffer(pageBuffer)
        EvtOpIds.START_TIMER.value -> StartTimer.fromByteBuffer(pageBuffer)
        EvtOpIds.SET_TIMER_VALUE_IN_COUNTER.value -> SetTimerValueInCounter.fromByteBuffer(pageBuffer)
        EvtOpIds.END.value -> OperationEnd(bytes = pageBuffer.array().toList())
        else -> UnimplementedOperation(
            opId = opId,
            opSize = opSize,
            bytes = bytes.toList()
        )
    }
}
