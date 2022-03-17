package com.y9vad9.construct.ksp.internal

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter

internal fun isViewGroup(declaration: KSClassDeclaration): Boolean {
    val functions = declaration.getAllFunctions()
    val isViewType: (KSValueParameter) -> Boolean =
        isViewType@{
            val declaration = it.type.resolve().declaration
            return@isViewType declaration.simpleName.asString() == "View" && declaration.packageName.asString() == "android.view"
        }
    return functions.any { it.simpleName.asString() == "addView" && isViewType(it.parameters[0]) }
}