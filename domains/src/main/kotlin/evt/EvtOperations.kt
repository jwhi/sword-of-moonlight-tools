package com.jwhi.som.domains.evt

import com.jwhi.som.domains.helpers.readNullTerminatedStrings
import java.nio.ByteBuffer

/**
 * EVT Operations
 * EVT operations can either be present in a file or not,
 * and in any order.
 *
 * These are what is stored at the payloadOffset within an EVT_PAGE.
 *
 */
interface EvtOperation {
    // Operation ID. See operation definitions below for valid/known values.
    val opId: UShort

    // Size of the operation (including the EVT_OPCODE.)
    val opSize: UShort
    // Debug to view and validate bytes
    val bytes: List<Byte>
}

data class EvtOpDisplayMessage(
    // opID = 0, opSize = variable
    override val opId: UShort = 0u,
    override val opSize: UShort,
    // Length is opSize - 4 bytes
    val text: String,
    override val bytes: List<Byte> = emptyList()
): EvtOperation

data class EvtOpDisplayMessageFormat(
    // opID = 1, opSize = variable
    override val opId: UShort = 1u,
    override val opSize: UShort,
    // RGBX format (8-bits per component)
    val textColour: UInt,
    // GDI font weight (0-550 = Normal, 551-999 Bold)
    val fontWeight: UShort,
    val padding1: UShort,
    val text: String,
    val textTerminator: UByte,
    val fontName: String,
    val fontNameTerminator: UByte,
    val padding2: UByte,
    override val bytes: List<Byte> = emptyList()
): EvtOperation

// 0 = Set To, 1 = Increment By, 2 = Decrement By
enum class EvtOpCounterMode(val value: UByte) {
    SET_TO(0u),
    INCREMENT_BY(1u),
    DECREMENT_BY(2u)
}

/*
90 - Change Counter's Value
    | event structure |
    00 # buffer
    0C 00 # number of bytes for this event block (starting from EventType)
    46 00 # Counter
    64 00 #Value
    01 # bool 00 - 01 Value / Counter's Value
    02 # Conditional 00 - 03 (SetTo, Raise, Lower)
    00 00 # buffer
*/
data class EvtOpChangeCounter(
    // opID = 144, opSize = 12
    // 0x90 0x00
    override val opId: UShort = 144u,
    override val opSize: UShort = 12u,
    // An exact value to use
    val value: UShort,
    // A counter to get the value from
    val targetCounter: UShort,
    // When 0 = use exact value,
    // when 1 = use value from targetCounter
    val useTarget: Boolean,
    val mode: EvtOpCounterMode,
    val padding: UShort,
    override val bytes: List<Byte> = emptyList()
): EvtOperation

// 0 = Forward, 1 = Back, 2 = Specific
enum class EvtOpChangePageType(val value: UByte) {
    FORWARD(0u),
    BACK(1u),
    SPECIFIC(2u)
}
data class EvtOpChangePage(
    // opID = 145, opSize = variable
    override val opId: UShort = 145u,
    override val opSize: UShort,
    // 0x000 -> 0x3FF = other,
    // 0xFFFF = self
    val target: Short,
    val changeType: EvtOpChangePageType,
    // ID to use when changeType == 2 (0 - 15)
    val changeSpecificId: UByte,
    override val bytes: List<Byte> = emptyList()
): EvtOperation

data class EvtOpUnimplemented(
    override val opId: UShort,
    override val opSize: UShort,
    override val bytes: List<Byte>
): EvtOperation

data class EvtOpIfMessage(
    // opID = 141 (-115 signed short) (0x8D 0x00), opSize = variable
    override val opId: UShort = 0u,
    override val opSize: UShort,
    // Length is not defined but is null terminated
    val text: String,
    val option1: String,
    val option2: String,
    val conditionMet: List<EvtOperation> = emptyList(),
    val otherwiseMet: List<EvtOperation> = emptyList(),
    override val bytes: List<Byte> = emptyList()
): EvtOperation {
    constructor(
        opId: UShort,
        opSize: UShort,
        bytes: ByteArray,
        textLines: List<String>,
        ): this(
        opId = opId,
        opSize = opSize,
        text = textLines[0],
        option1 = textLines[1],
        option2 = textLines[2],
        bytes = bytes.toList()
    )

    constructor(
        opId: UShort,
        opSize: UShort,
        bytes: ByteBuffer
    ): this(
        opId = opId,
        opSize = opSize,
        textLines = readNullTerminatedStrings(bytes, 3),
        bytes = bytes.array()
    )
}


data class EvtOpEnd(
    // opID = 255 (-1 signed), opSize = 4
    override val opId: UShort = 0xFFu.toUShort(),
    override val opSize: UShort = 4u,
    override val bytes: List<Byte> = emptyList()
): EvtOperation

enum class EventIds(val value: UShort) {
    MESSAGE(0u),
    IF_MESSAGE(141u),
    END(255u)
}
