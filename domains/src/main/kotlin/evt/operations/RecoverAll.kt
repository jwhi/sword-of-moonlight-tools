package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds

data class RecoverAll(
    override val opId: UShort = EvtOpIds.RECOVER_ALL.value,
    override val opSize: UShort = 4u,
    override val bytes: List<Byte>
): EvtOperation
