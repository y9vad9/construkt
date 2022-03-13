package com.y9vad9.construct.ksp.internal

import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

internal fun Sequence<KSFunctionDeclaration>.filterFunctions() = filter {
    val name = it.simpleName.asString()
    name.startsWith("set") && it.isPublic()
}