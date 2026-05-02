package com.jwhi.som.domains.evt

enum class EvtOffsets(val byte: Byte) {
    NAME(0x00),
    TARGET_TYPE(0x1F),
    TARGET_ID(0x20),
    TRIGGER_TYPE(0x22),
    TRIGGER_ITEM(0x23),
    VIEWING_CONE(0x24),
    UNKNOWN(0x26),
    TRIGGER_RECTANGLE_WEST_EAST(0x28),
    TRIGGER_RECTANGLE_NORTH_SOUTH(0x2C),
    TRIGGER_CIRCLE_RADIUS(0x30),
    CHECK_TYPE(0x34),
    CHECK_TARGET_ID(0x36),
    COMPARISON_VALUE(0x38),
    COMPARISON_TYPE(0x3A),
    PAGES_START(0x3C);

    fun intOffset() = this.byte.toInt()
}

enum class EvtPageOffsets(val byte: Byte) {
    DATA_OFFSET(0x00),
    CHECK_TYPE(0x04),
    CHECK_TARGET_ID(0x06),
    COMPARISON_VALUE(0x08),
    COMPARISON_TYPE(0x10);

    fun intOffset() = this.byte.toInt()
}

enum class EvtPageDataOffsets(val byte: Byte) {
    OPERATION_TYPE(0x00),
    OPERATION_SIZE(0x02)
}
