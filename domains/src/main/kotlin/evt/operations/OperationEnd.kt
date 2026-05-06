package com.jwhi.som.domains.evt.operations

data class OperationEnd(
    // opID = 255 (-1 signed), opSize = 4
    override val opId: UShort = UShort.MAX_VALUE,
    override val opSize: UShort = 4u,
    override val bytes: List<Byte> = emptyList()
): EvtOperation
