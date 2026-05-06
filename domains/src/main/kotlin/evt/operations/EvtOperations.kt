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
        EvtOpIds.SET_PLAYER_PARAMETER_IN_COUNTER.value -> SetPlayerParameterInCounter.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
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
        EvtOpIds.OTHERWISE.value -> Otherwise()
        EvtOpIds.END_IF.value -> EndIf()
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
        EvtOpIds.END.value -> OperationEnd()
        else -> UnimplementedOperation(
            opId = opId,
            opSize = opSize,
            bytes = bytes.toList()
        )
    }
}
