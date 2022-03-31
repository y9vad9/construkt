package com.construkt.matching

import com.construkt.models.ResolvedViewModel
import com.construkt.types.Android
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.toTypeParameterResolver

fun KSFunctionDeclaration.isMenuFunction(): Boolean {
    val type = returnType?.resolve()?.declaration ?: return false
    return type.packageName.asString() == Android.Menu.packageName
        && type.simpleName.asString() == Android.Menu.simpleName
}

fun KSFunctionDeclaration.isSetFunction(): Boolean {
    return simpleName.asString().startsWith("set") && parameters.isNotEmpty()
}

fun KSFunctionDeclaration.returnTypeIn(other: List<ResolvedViewModel.ScopedType>): Boolean {
    val resolved = returnType!!.resolve()
    return other.any {
        resolved.declaration.packageName.asString() == it.type.packageName
        && resolved.declaration.simpleName.asString() == it.type.simpleName }
}

@JvmName("returnTypeInWrapped")
fun KSFunctionDeclaration.getWrappedWithReturnType(other: List<ResolvedViewModel.WrappedType>): ResolvedViewModel.WrappedType? {
    val resolved = returnType!!.resolve()
    return other.firstOrNull { resolved.declaration.packageName.asString() == it.forType.packageName
        && resolved.declaration.simpleName.asString() == it.forType.simpleName }
}

@JvmName("returnTypeInWrapped")
fun KSFunctionDeclaration.returnTypeIn(other: List<ResolvedViewModel.WrappedType>): Boolean {
    return getWrappedWithReturnType(other) != null
}