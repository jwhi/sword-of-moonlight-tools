package com.jwhi.som.domains.evt


/**
 * EVT Header
 */
data class EvtHeader(
    // Number of events in the EVT file (always 1024)
    val magicBytes: UInt
) {
    companion object {
        val BYTE_HEADER: List<Byte> = listOf(0x00, 0x04, 0x00, 0x00)
    }
}

/**
 * Condition Mode.
 * 0 = None,
 * 1 = Item Quantity,
 * 2 = Gold Quantity,
 * 3 = Strength,
 * 4 = Magic,
 * 5 = Level,
 * 6 = Counter
 */
enum class CompareType(val value: UShort) {
    NONE(0u),
    ITEM_QUANTITY(1u),
    GOLD_QUANTITY(2u),
    STRENGTH(3u),
    MAGIC(4u),
    LEVEL(5u),
    COUNTER(6u);

    companion object {
        private val mapping = entries.associateBy(CompareType::value)
        fun from(value: UShort) = mapping[value]!! // Or default to ?: NONE
    }
}

// 0 = Equals
// 1 = Not Equals
// 2 = Greater Than
// 3 = Less Than
enum class ComparisonType(val value: UShort) {
    EQUALS(0u),
    NOT_EQUALS(1u),
    GREATER_THAN(2u),
    LESS_THAN(3u);

    companion object {
        private val mapping = ComparisonType.entries.associateBy(ComparisonType::value)
        fun from(value: UShort) = mapping[value]!! // Or default to something
    }
}

data class EvtCondition(
    val compareType: CompareType,
    // When compareType == 1 (ITEM_QUANTITY):
    // 0x00->0xF9 = Item ID,
    // 0xFF = None.
    // When compareType == 6 (Counter):
    // 0x0000->0x3FF = Counter ID
    val compareId: UShort,
    // The value to check against in various modes. Not allowed to be negative
    val comparedValue: UShort,
    val comparisonType: ComparisonType
)

data class EvtPage(
    // op code offset in file.
    val payloadOffset: UInt,
    // Condition to be met in order for page to run
    val startCondition: EvtCondition
)

// 0 = NPC, 1 = ENEMY, 2 = OBJECT, -2 = SYSTEM, -1 = NONE
enum class TargetType(val value: UByte) {
    NPC(0x00u),
    ENEMY(0x01u),
    OBJECT(0x02u),
    SYSTEM(0xFEu),
    NONE(0xFFu);

    companion object {
        private val mapping = TargetType.entries.associateBy(TargetType::value)
        fun from(value: UByte) = mapping[value]!! // Or default to ?: NONE
    }
}

// 0x01 = Examine
// 0x02 = Use Item
// 0x04 = Approach (Rectangle)
// 0x08 = Approach (Circle)
// 0x10 = Death (Enemy/NPC)
// 0x20 = Always On
// 0x40 = Use Item
// 0x00 = None
enum class TriggerType(val value: UByte) {
    EXAMINE(0x01u),
    USE_ITEM(0x02u),
    APPROACH_SQUARE(0x04u),
    APPROACH_CIRCLE(0x08u),
    DEATH_ENEMY_NPC(0x10u),
    ALWAYS_ON(0x20u),
    USE_ITEM_(0x40u),
    NONE(0x00u);

    companion object {
        private val mapping = TriggerType.entries.associateBy(TriggerType::value)
        fun from(value: UByte) = mapping[value]!! // Or default to something
    }
}

data class EvtDefinition(
    // char name[31];
    val name: String,
    val targetType: TargetType,
    // Map ID of npc/enemy/object
    val targetId: UShort,
    val triggerType: TriggerType,
    // 0x00->0xF9 = Item ID, 0xFF = None
    val triggerItem: UByte,
    // View activation cone (degrees)
    val triggerCone: UShort,
    // Padding? - Not implemented?
    val padding: UShort,
    // West -> East rectangle Coverage
    val triggerRectWE: Float,
    // North -> South rectangle coverage
    val triggerRectNS: Float,
    // Trigger radius
    val triggerRadius: Float,
    // Event start condition.
    val condition: EvtCondition,
    // Event page declarations.
    // EVT_PAGE pages[16];
    val pages: List<EvtPage>
)
