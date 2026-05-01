package com.jwhi.som.domains

import kotlinx.serialization.SerializationException
import java.io.IOException

class InvalidEvtException(
    message: String,
    cause: Throwable? = null
) : Exception("Invalid map event file: $message", cause)
