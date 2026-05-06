package com.jwhi.som.domains.evt.operations

enum class WayChanged(val value: UByte) {
    SET_TO(0u),
    INCREMENT_BY(1u),
    DECREMENT_BY(2u),
    COUNTER(3u);

    companion object {
        private val mapping = entries.associateBy(WayChanged::value)
        fun from(byte: UByte): WayChanged = mapping[byte]!!
    }
}
