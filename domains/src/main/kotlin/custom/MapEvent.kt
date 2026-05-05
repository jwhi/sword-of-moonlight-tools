package com.jwhi.som.domains.custom

import com.jwhi.som.domains.evt.EvtDefinition
import com.jwhi.som.domains.evt.EvtOperation

data class MapEvent(
    val definition: EvtDefinition,
    val pageOperations: Map<UInt, List<EvtOperation>>
)
