package com.jwhi.som.domains.evt

import com.jwhi.som.domains.helpers.getNullTerminatedString
import com.jwhi.som.domains.helpers.readString
import com.jwhi.som.domains.helpers.readUntil
import java.nio.ByteBuffer
import java.nio.ByteOrder

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

fun ByteArray.parseEvtOperation(): EvtOperation {
    return ByteBuffer.wrap(this).order(ByteOrder.LITTLE_ENDIAN).parseEvtOperation(0)
}

fun ByteBuffer.parseEvtOperation(offset: Int): EvtOperation {
    this.position(offset)
    val opId = this.getShort().toUShort()
    val opSize = this.getShort().toUShort()
    val bytes = this.array()
    val pageBuffer = this

    return when(opId) {
        0.toUShort() -> EvtOpDisplayMessage(
            opId = opId,
            opSize = opSize,
            text = this.getNullTerminatedString(),
            bytes = bytes.toList()
        )
        1.toUShort() -> EvtOpDisplayMessageFormat.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        141.toUShort() -> EvtOpIfMessage.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        else -> EvtOpGeneric(
            opId = opId,
            opSize = opSize,
            bytes = bytes.toList()
        )
    }
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
    val textColorRed: Byte,
    val textColorGreen: Byte,
    val textColorBlue: Byte,
    val textColorExtra: Byte,
    // GDI font weight (0-550 = Normal, 551-999 Bold)
    val fontWeight: UShort,
    val fontWeightPadding: UShort,
    val text: String,
    val fontName: String,
    override val bytes: List<Byte> = emptyList()
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            opId: UShort,
            opSize: UShort,
            buffer: ByteBuffer
        ): EvtOpDisplayMessageFormat {

            return EvtOpDisplayMessageFormat(
                opId = opId,
                opSize = opSize,
                textColorRed = buffer.get(),
                textColorGreen = buffer.get(),
                textColorBlue = buffer.get(),
                textColorExtra = buffer.get(),
                fontWeight = buffer.getShort().toUShort(),
                fontWeightPadding = buffer.getShort().toUShort(),
                text = buffer.getNullTerminatedString(),
                fontName = buffer.getNullTerminatedString()
            )
        }
    }
}

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

data class EvtOpGeneric(
    override val opId: UShort,
    override val opSize: UShort,
    override val bytes: List<Byte>
): EvtOperation

data class EvtIfBlock(
    val ifBlock: List<EvtOperation>,
    val otherwiseBlock: List<EvtOperation>
)

data class EvtOpIfMessage(
    // opID = 141 (-115 signed short) (0x8D 0x00), opSize = variable
    override val opId: UShort = 0u,
    override val opSize: UShort,
    // Length is not defined but is null terminated
    val text: String,
    val option1: String,
    val option2: String,
    override val bytes: List<Byte> = emptyList()
): EvtOperation {

    companion object {
        fun fromByteBuffer(
            opId: UShort,
            opSize: UShort,
            buffer: ByteBuffer
        ): EvtOpIfMessage {
            val bytes = buffer.array()
            val message = buffer.getNullTerminatedString()
            val option1 = buffer.getNullTerminatedString()
            val option2 = buffer.getNullTerminatedString()
            /*
            val ifBlockBytes = buffer.readUntil(
                byteArrayOf(
                    0x8E.toByte(),
                    0x00,
                    0x04,
                    0x00
                )
            )

             */
            // val ifBlock = ifBlockBytes.parseEvtOperation()
            // val otherwiseBlockBytes = buffer.readUntil(byteArrayOf(0xFF.toByte()))
            // val otherwiseBlock = otherwiseBlockBytes.parseEvtOperation()
            return EvtOpIfMessage(
                opId = opId,
                opSize = opSize,
                text = message,
                option1 = option1,
                option2 = option2,
                bytes = bytes.toList()
            )
        }
    }
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
