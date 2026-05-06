package com.jwhi.som.domains.evt.operations

import com.jwhi.som.domains.evt.EvtOpIds

// (opId=80, opSize=12, bytes=[80, 0, 12, 0, 4, 2, 0, 0, 0, 0, 1, 0])
data class ChangePlayerParameter(
    override val opId: UShort = EvtOpIds.CHANGE_PLAYER_PARAMETER.value,
    override val opSize: UShort = 12u,
    val playerParameter: PlayerParameter,
    val wayChanged: WayChanged,
    val itemId: UShort,
    val counterId: UShort,
    val value: UShort,

    override val bytes: List<Byte>
): EvtOperation