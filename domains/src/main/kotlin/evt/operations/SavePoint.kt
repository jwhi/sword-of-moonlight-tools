package com.jwhi.som.domains.evt.operations

data class SavePoint(
    override val opId: UShort = UShort.MAX_VALUE,
    override val opSize: UShort = 4u,
    override val bytes: List<Byte>
): EvtOperation
