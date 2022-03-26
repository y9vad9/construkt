package com.construkt.matching

import com.construkt.types.Android
import com.google.devtools.ksp.symbol.KSPropertyDeclaration

fun KSPropertyDeclaration.isMenuProperty(): Boolean {
    val type = type.resolve().declaration
    return type.packageName.asString() == Android.Menu.packageName
        && type.simpleName.asString() == Android.Menu.simpleName
}

fun KSPropertyDeclaration.isMutable(): Boolean {
    return isMutable
}