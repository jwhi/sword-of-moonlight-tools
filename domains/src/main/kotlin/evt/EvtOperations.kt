package com.jwhi.som.domains.evt

import com.jwhi.som.domains.helpers.getNullTerminatedString
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
        EvtOpIds.MESSAGE.value -> EvtOpDisplayMessage.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.FORMAT_MESSAGE.value -> EvtOpDisplayMessageFormat.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.SET_PLAYER_PARAMETER_TO_COUNTER.value -> EvtOpSetPlayerParameterInCounter.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.IF_COUNTER.value -> EvtOpIfCounter.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.IF_MESSAGE.value -> EvtOpIfMessage.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.OTHERWISE.value -> EvtOpOtherwise()
        EvtOpIds.END_IF.value -> EvtOpEndIf()
        EvtOpIds.CHANGE_COUNTER.value -> EvtOpChangeCounter.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.CHANGE_PAGE.value -> EvtOpChangePage.fromByteBuffer(
            opId = opId,
            opSize = opSize,
            buffer = pageBuffer
        )
        EvtOpIds.RETURN.value -> EvtOpReturn()
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
): EvtOperation {
    companion object {
        fun fromByteBuffer(
        opId: UShort,
        opSize: UShort,
        buffer: ByteBuffer
    ): EvtOpDisplayMessage {
            return EvtOpDisplayMessage(
                opId = opId,
                opSize = opSize,
                text = buffer.getNullTerminatedString(),
                bytes = buffer.array().toList()
            )
        }
    }
}

data class EvtOpDisplayMessageFormat(
    // opID = 1, opSize = variable
    override val opId: UShort = 1u,
    override val opSize: UShort,
    // RGBX format (8-bits per component)
    val textColorRed: UByte,
    val textColorGreen: UByte,
    val textColorBlue: UByte,
    val textColorExtra: UByte,
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
                textColorRed = buffer.get().toUByte(),
                textColorGreen = buffer.get().toUByte(),
                textColorBlue = buffer.get().toUByte(),
                textColorExtra = buffer.get().toUByte(),
                fontWeight = buffer.getShort().toUShort(),
                fontWeightPadding = buffer.getShort().toUShort(),
                text = buffer.getNullTerminatedString(),
                fontName = buffer.getNullTerminatedString(),
                bytes = buffer.array().toList()
            )
        }
    }
}

enum class EvtOpCounterMode(val value: UByte) {
    SET_TO(0u),
    INCREMENT_BY(1u),
    DECREMENT_BY(2u),
    COUNTER(3u);

    companion object {
        private val mapping = EvtOpCounterMode.entries.associateBy(EvtOpCounterMode::value)
        fun from(byte: UByte): EvtOpCounterMode = mapping[byte]!!
    }
}

data class EvtOpChangeCounter(
    // opID = 144,
    // 0x90 0x00
    override val opId: UShort = 144u,
    override val opSize: UShort = 12u,
    val counterId: UShort,
    val exactValue: UShort,
    // When 0 = use exact value,
    // when 1 = use value from sourceCounter
    val useTarget: Boolean,
    val mode: EvtOpCounterMode,
    val sourceCounter: UShort,
    override val bytes: List<Byte> = emptyList()
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            opId: UShort,
            opSize: UShort,
            buffer: ByteBuffer
        ): EvtOpChangeCounter {
            return EvtOpChangeCounter(
                opId = opId,
                opSize = opSize,
                counterId = buffer.getShort().toUShort(),
                exactValue = buffer.getShort().toUShort(),
                useTarget = buffer.get() == 0x01.toByte(),
                mode = EvtOpCounterMode.from(buffer.get().toUByte()),
                sourceCounter = buffer.getShort().toUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}

// 0 = Forward, 1 = Back, 2 = Specific
enum class EvtOpChangePageType(val value: UByte) {
    FORWARD(0u),
    BACK(1u),
    SPECIFIC(2u);

    companion object {
        private val mapping = entries.associateBy(EvtOpChangePageType::value)
        fun from(byte: UByte): EvtOpChangePageType = mapping[byte]!!
    }
}
data class EvtOpChangePage(
    // opID = 145, opSize = variable
    override val opId: UShort = 145u,
    override val opSize: UShort,
    // 0x000 -> 0x3FF = other,
    // 0xFFFF = self
    val target: UShort,
    val changeType: EvtOpChangePageType,
    // ID to use when changeType == 2 (0 - 15)
    val changeSpecificId: UByte,
    override val bytes: List<Byte> = emptyList()
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            opId: UShort,
            opSize: UShort,
            buffer: ByteBuffer
        ) = EvtOpChangePage(
            opId = opId,
            opSize = opSize,
            target = buffer.getShort().toUShort(),
            changeType = EvtOpChangePageType.from(buffer.get().toUByte()),
            changeSpecificId = buffer.get().toUByte(),
            bytes = buffer.array().toList()
        )
    }
}

data class EvtOpGeneric(
    override val opId: UShort,
    override val opSize: UShort,
    override val bytes: List<Byte>
): EvtOperation

enum class EvtOpPlayerParameters(val value: UByte) {
    HP(0x00u),
    MP(0x01u),
    STRENGTH_STAT(0x02u),
    MAGIC_STAT(0x03u),
    ITEM_QUANTITY(0x04u),
    GOLD_AMOUNT(0x05u),
    LEVEL(0x06u);

    companion object {
        private val mappings = entries.associateBy(EvtOpPlayerParameters::value)
        fun from(byte: UByte): EvtOpPlayerParameters = mappings[byte]!!
    }
}

enum class EvtOpCompareType(val value: UByte) {
    EQUALS(0u),
    NOT_EQUALS(1u),
    GREATER_THAN(2u),
    LESS_THAN(3u),
    NONE(0xFFu);

    companion object {
        private val mapping = entries.associateBy(EvtOpCompareType::value)
        fun from(value: UByte) = mapping[value] ?: NONE // Or default to something
    }
}
data class EvtOpSetPlayerParameterInCounter(
    override val opId: UShort = 84u,
    override val opSize: UShort,
    val playerParameter: EvtOpPlayerParameters,
    val itemId: UByte,
    val targetCounter: UShort,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            opId: UShort,
            opSize: UShort,
            buffer: ByteBuffer
        ): EvtOpSetPlayerParameterInCounter {
            return EvtOpSetPlayerParameterInCounter(
                opId = opId,
                opSize = opSize,
                playerParameter = EvtOpPlayerParameters.from(buffer.get().toUByte()),
                itemId = buffer.get().toUByte(),
                targetCounter = buffer.get().toUShort(),
                bytes = buffer.array().toList()
            )
        }
    }
}

data class EvtOpIfCounter(
    override val opId: UShort = 140u,
    override val opSize: UShort,
    val counterId: UShort,
    val value: UShort,
    val valueIsCounterId: Boolean,
    val compareType: EvtOpCompareType,
    override val bytes: List<Byte>
): EvtOperation {
    companion object {
        fun fromByteBuffer(
            opId: UShort,
            opSize: UShort,
            buffer: ByteBuffer
        ): EvtOpIfCounter {
            return EvtOpIfCounter(
                opId = opId,
                opSize = opSize,
                counterId = buffer.getShort().toUShort(),
                value = buffer.getShort().toUShort(),
                valueIsCounterId = buffer.get() == 0x01.toByte(),
                compareType = EvtOpCompareType.from(buffer.get().toUByte()),
                bytes = buffer.array().toList()
            )
        }
    }
}

data class EvtOpIfMessage(
    // opID = 141 (-115 signed short) (0x8D 0x00), opSize = variable
    override val opId: UShort,
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

data class EvtOpOtherwise(
    override val opId: UShort = 142u,
    override val opSize: UShort = 0x04u,
    override val bytes: List<Byte> = emptyList()
): EvtOperation

data class EvtOpEndIf(
    override val opId: UShort = 143u,
    override val opSize: UShort = 0x04u,
    override val bytes: List<Byte> = emptyList()
): EvtOperation



data class EvtOpReturn(
    // opID = 255 (-1 signed), opSize = 4
    override val opId: UShort = UShort.MAX_VALUE,
    override val opSize: UShort = 4u,
    override val bytes: List<Byte> = emptyList()
): EvtOperation
