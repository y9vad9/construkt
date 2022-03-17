package com.construkt.internal.extensions

internal fun String.formatFunctionName(): String {
    return removePrefix("set").replaceFirstChar { it.lowercase() }
}