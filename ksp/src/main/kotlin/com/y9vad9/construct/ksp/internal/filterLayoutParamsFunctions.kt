package com.y9vad9.construct.ksp.internal

import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

//TODO normal check
private val names = listOf("equals", "toString", "debug")

fun Sequence<KSFunctionDeclaration>.filterLayoutParamsFunctions(): Sequence<KSFunctionDeclaration> {
    return filter {
        it.parameters.isNotEmpty()
            && !it.simpleName.asString().contains("<")
            && it.isPublic()
            && it.simpleName.asString() !in names
    }
}