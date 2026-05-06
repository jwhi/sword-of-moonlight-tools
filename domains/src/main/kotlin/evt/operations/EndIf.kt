package com.jwhi.som.domains.evt.operations

data class EndIf(
    override val opId: UShort = 143u,
    override val opSize: UShort = 0x04u,
    override val bytes: List<Byte> = emptyList()
): EvtOperation
