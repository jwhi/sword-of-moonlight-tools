package com.jwhi.som.domains.evt.operations

data class Otherwise(
    override val opId: UShort = 142u,
    override val opSize: UShort = 0x04u,
    override val bytes: List<Byte> = emptyList()
): EvtOperation
