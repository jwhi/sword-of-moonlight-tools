package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds

data class EndIf(
    override val opId: UShort = EvtOpIds.END_IF.value,
    override val opSize: UShort = 0x04u,
    override val bytes: List<Byte> = emptyList()
): EvtOperation
