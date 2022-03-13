package com.y9vad9.construct.ksp.internal

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType

internal fun resolveLayoutParams(classDeclaration: KSClassDeclaration): KSType {
    return classDeclaration.getAllFunctions().first {
        it.simpleName.asString() == "setLayoutParams" && it.parameters.singleOrNull()?.type != null
    }.parameters[0].type.resolve()
}