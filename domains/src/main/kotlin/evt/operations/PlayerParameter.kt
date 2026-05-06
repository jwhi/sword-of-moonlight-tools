package com.jwhi.som.domains.evt.operations

enum class PlayerParameter(val value: UByte) {
    HP(0x00u),
    MP(0x01u),
    STRENGTH_STAT(0x02u),
    MAGIC_STAT(0x03u),
    ITEM_QUANTITY(0x04u),
    GOLD_AMOUNT(0x05u),
    // Not allowed to update through Change Player Parameter operation
    LEVEL(0x06u);

    companion object {
        private val mappings = entries.associateBy(PlayerParameter::value)
        fun from(byte: UByte): PlayerParameter = mappings[byte]!!
        fun from(uShortValue: UShort): PlayerParameter = mappings[uShortValue.toUByte()]!!
    }
}
