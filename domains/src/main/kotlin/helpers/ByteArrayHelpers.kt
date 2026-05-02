package com.jwhi.som.domains.helpers

import java.nio.ByteBuffer
import java.nio.ByteOrder

// Pulled from https://github.com/mvysny/kotlin-unsigned-jvm

/**
 * Returns the (possibly negative) byte represented by the byte at the
 * specified [byteOffset] in this object, in two's complement binary
 * representation.
 *
 * The return value will be between -128 and 127, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * less than the length of this object.
 */
fun ByteArray.getByte(byteOffset: Int): Byte = get(byteOffset)

/**
 * Sets the byte at the specified [byteOffset] in this object to the
 * two's complement binary representation of the specified [value], which
 * must fit in a single byte.
 *
 * In other words, [value] must be between -128 and 127, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * less than the length of this object.
 */
fun ByteArray.setByte(byteOffset: Int, value: Byte) {
    set(byteOffset, value)
}

/**
 * Sets the byte at the specified [byteOffset] in this object to the
 * two's complement binary representation of the specified [value], which
 * must fit in a single byte.
 *
 * In other words, [value] must be between -128 and 127, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * less than the length of this object.
 */
fun ByteArray.setByte(byteOffset: Int, value: Int) {
    set(byteOffset, value.toByte())
}

/**
 * Returns the positive integer represented by the byte at the specified
 * [byteOffset] in this object, in unsigned binary form.
 *
 * The return value will be between 0 and 255, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * less than the length of this object.
 */
fun ByteArray.getUByte(byteOffset: Int): UByte = getByte(byteOffset).toUByte()

/**
 * Sets the byte at the specified [byteOffset] in this object to the
 * unsigned binary representation of the specified [value], which must fit
 * in a single byte.
 *
 * In other words, [value] must be between 0 and 255, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * less than the length of this object.
 */
fun ByteArray.setUByte(byteOffset: Int, value: UInt) {
    set(byteOffset, value.toByte())
}

/**
 * Sets the byte at the specified [byteOffset] in this object to the
 * unsigned binary representation of the specified [value], which must fit
 * in a single byte.
 *
 * In other words, [value] must be between 0 and 255, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * less than the length of this object.
 */
fun ByteArray.setUByte(byteOffset: Int, value: UByte) {
    set(byteOffset, value.toByte())
}

/**
 * Sets the two bytes starting at the specified [byteOffset] in this
 * object to the two's complement binary representation of the specified
 * [value].
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 2` must be less than or equal to the length of [this].
 */
fun ByteArray.setShort(byteOffset: Int, value: Short, endian: Endian = Endian.DEFAULT) {
    endian.setShort(this, byteOffset, value)
}

/**
 * Returns the (possibly negative) integer represented by the two bytes at
 * the specified [byteOffset] in this object, in two's complement binary
 * form.
 *
 * The return value will be between -2^15 and 2^15 - 1,
 * inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 2` must be less than or equal to the length of this object.
 */
fun ByteArray.getShort(
    byteOffset: Int,
    endian: Endian = Endian.DEFAULT
): Short = endian.getShort(this, byteOffset)

/**
 * Sets the two bytes starting at the specified [byteOffset] in this
 * object to the two's complement binary representation of the specified
 * [value]. Only the lowest 16 bits are considered, high 16 bits are ignored.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 2` must be less than or equal to the length of [this].
 */
fun ByteArray.setShort(byteOffset: Int, value: Int, endian: Endian = Endian.DEFAULT) {
    endian.setShort(this, byteOffset, value)
}

/**
 * Returns the positive integer represented by the two bytes starting
 * at the specified [byteOffset] in this object, in unsigned binary
 * form.
 *
 * The return value will be between 0 and  2^16 - 1, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 2` must be less than or equal to the length of this object.
 */
fun ByteArray.getUShort(byteOffset: Int, endian: Endian = Endian.DEFAULT): UShort = endian.getUShort(this, byteOffset)

/**
 * Sets the two bytes starting at the specified [byteOffset] in this object
 * to the unsigned binary representation of the specified [value],
 * which must fit in two bytes.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 2` must be less than or equal to the length of [this].
 */
fun ByteArray.setUShort(byteOffset: Int, value: UShort, endian: Endian = Endian.DEFAULT) {
    endian.setUShort(this, byteOffset, value)
}

/**
 * Sets the two bytes starting at the specified [byteOffset] in this object
 * to the unsigned binary representation of the specified [value],
 * which must fit in two bytes.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 2` must be less than or equal to the length of [this].
 */
fun ByteArray.setUShort(byteOffset: Int, value: UInt, endian: Endian = Endian.DEFAULT) {
    endian.setUShort(this, byteOffset, value)
}


/**
 * Returns the (possibly negative) integer represented by the four bytes at
 * the specified [byteOffset] in this object, in two's complement binary
 * form.
 *
 * The return value will be between -2^31 and 2^31 - 1,
 * inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 4` must be less than or equal to the length of this object.
 */
fun ByteArray.getInt(byteOffset: Int, endian: Endian = Endian.DEFAULT): Int = endian.getInt(this, byteOffset)

/**
 * Sets the four bytes starting at the specified [byteOffset] in this
 * object to the two's complement binary representation of the specified
 * [value], which must fit in four bytes.
 *
 * In other words, [value] must lie
 * between -2^31 and 2^31 - 1, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 4` must be less than or equal to the length of this object.
 */
fun ByteArray.setInt(byteOffset: Int, value: Int, endian: Endian = Endian.DEFAULT) {
    endian.setInt(this, byteOffset, value)
}

/**
 * Returns the positive integer represented by the four bytes starting
 * at the specified [byteOffset] in this object, in unsigned binary
 * form.
 *
 * The return value will be between 0 and  2^32 - 1, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 4` must be less than or equal to the length of this object.
 */
fun ByteArray.getUInt(byteOffset: Int, endian: Endian = Endian.DEFAULT): UInt = endian.getUInt(this, byteOffset)

/**
 * Sets the four bytes starting at the specified [byteOffset] in this object
 * to the unsigned binary representation of the specified [value],
 * which must fit in four bytes.
 *
 * In other words, [value] must be between
 * 0 and 2^32 - 1, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 4` must be less than or equal to the length of this object.
 */
fun ByteArray.setUInt(byteOffset: Int, value: UInt, endian: Endian = Endian.DEFAULT) {
    endian.setUInt(this, byteOffset, value)
}


/**
 * Returns the (possibly negative) integer represented by the eight bytes at
 * the specified [byteOffset] in this object, in two's complement binary
 * form.
 *
 * The return value will be between -2^63 and 2^63 - 1,
 * inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 8` must be less than or equal to the length of this object.
 */
fun ByteArray.getLong(byteOffset: Int, endian: Endian = Endian.DEFAULT): Long = endian.getLong(this, byteOffset)

/**
 * Sets the eight bytes starting at the specified [byteOffset] in this
 * object to the two's complement binary representation of the specified
 * [value], which must fit in eight bytes.
 *
 * In other words, [value] must lie
 * between -2^63 and 2^63 - 1, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 8` must be less than or equal to the length of this object.
 */
fun ByteArray.setLong(byteOffset: Int, value: Long, endian: Endian = Endian.DEFAULT) {
    endian.setLong(this, byteOffset, value)
}

/**
 * Returns the positive integer represented by the eight bytes starting
 * at the specified [byteOffset] in this object, in unsigned binary
 * form.
 *
 * The return value will be between 0 and  2^64 - 1, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 8` must be less than or equal to the length of this object.
 */
fun ByteArray.getULong(byteOffset: Int, endian: Endian = Endian.DEFAULT): ULong = endian.getULong(this, byteOffset)

/**
 * Sets the eight bytes starting at the specified [byteOffset] in this object
 * to the unsigned binary representation of the specified [value],
 * which must fit in eight bytes.
 *
 * In other words, [value] must be between
 * 0 and 2^64 - 1, inclusive.
 *
 * The [byteOffset] must be non-negative, and
 * `byteOffset + 8` must be less than or equal to the length of this object.
 */
fun ByteArray.setULong(byteOffset: Int, value: ULong, endian: Endian = Endian.DEFAULT) {
    endian.setULong(this, byteOffset, value)
}

fun ByteArray.getFloat(byteOffset: Int): Float {
    // Source - https://stackoverflow.com/a/44068170
    // Posted by hotkey
    // Retrieved 2026-05-01, License - CC BY-SA 3.0
    val buffer = ByteBuffer.wrap(this).order(ByteOrder.LITTLE_ENDIAN)
    return buffer.getFloat(byteOffset)
}
