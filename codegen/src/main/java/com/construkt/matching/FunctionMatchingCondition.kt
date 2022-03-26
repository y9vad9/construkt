package com.construkt.matching

import com.construkt.types.Android
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import java.io.File

fun KSFunctionDeclaration.isMenuFunction(): Boolean {
    val type = returnType?.resolve()?.declaration ?: return false
    return type.packageName.asString() == Android.Menu.packageName
        && type.simpleName.asString() == Android.Menu.simpleName
}

fun KSFunctionDeclaration.isSetFunction(): Boolean {
    return simpleName.asString().startsWith("set") && parameters.isNotEmpty()
}

fun KSDeclaration.isNotPublic(): Boolean {
    return !isPublic()
}