package com.jwhi.som.domains

class InvalidEvtException(
    message: String,
    cause: Throwable? = null
) : Exception("Invalid map event file: $message", cause)
