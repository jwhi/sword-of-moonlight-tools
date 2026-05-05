package com.jwhi.som.domains.evt

enum class EvtOpIds(val value: UShort) {
    MESSAGE(0u),
    FORMAT_MESSAGE(1u),
    SET_PLAYER_PARAMETER_TO_COUNTER(84u),
    IF_COUNTER(140u),
    IF_MESSAGE(141u),
    OTHERWISE(142u),
    END_IF(143u),
    CHANGE_COUNTER(144u),
    CHANGE_PAGE(145u),
    RETURN(UShort.MAX_VALUE),
}
