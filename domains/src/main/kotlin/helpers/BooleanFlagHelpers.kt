package com.jwhi.som.domains.helpers

/*
 * For some event operations, check box values are stored
 * as a list of binary flags (like in Warp Player Basic)
 */
fun Byte.getBooleanFlags() : List<Boolean> {
    val value = this.toInt()
    return (0..3).map {
        val x = (value shr it)
        x % 2 == 1
    }
}