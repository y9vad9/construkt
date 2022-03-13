package com.y9vad9.construct.ksp.internal

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration

internal fun isViewGroup(declaration: KSClassDeclaration): Boolean {
    val functions = declaration.getAllFunctions()
    val isViewType: (KSDeclaration) -> Boolean =
        { it.simpleName.asString() == "View" && it.packageName.asString() == "android.view" }
    return functions.any { it.simpleName.asString() == "addView" && isViewType(it) }
}