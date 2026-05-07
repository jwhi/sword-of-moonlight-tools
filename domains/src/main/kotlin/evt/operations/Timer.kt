package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.helpers.getUShort
import java.nio.ByteBuffer

data class StartTimer(
    override val opId: UShort = EvtOpIds.START_TIMER.value,
    override val opSize: UShort = 8u,
    val timer: UShort,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): StartTimer {
            return StartTimer(
                timer = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}

data class SetTimerValueInCounter(
    override val opId: UShort = EvtOpIds.SET_TIMER_VALUE_IN_COUNTER.value,
    override val opSize: UShort = 8u,
    val timer: UShort,
    val counterId: UShort,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): SetTimerValueInCounter {
            return SetTimerValueInCounter(
                timer = buffer.getUShort(),
                counterId = buffer.getUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}
