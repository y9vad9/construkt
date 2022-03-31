package com.construkt.matching

import com.construkt.models.ResolvedViewModel
import com.construkt.types.Android
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.toTypeParameterResolver

fun KSPropertyDeclaration.isMenuProperty(): Boolean {
    val type = type.resolve().declaration
    return type.packageName.asString() == Android.Menu.packageName
        && type.simpleName.asString() == Android.Menu.simpleName
}

fun KSPropertyDeclaration.isMutable(): Boolean {
    return isMutable
}

fun KSPropertyDeclaration.getWrappedTypeFrom(values: List<ResolvedViewModel.WrappedType>): ResolvedViewModel.WrappedType? {
    val resolved = type.resolve()
    return values.firstOrNull { resolved.declaration.packageName.asString() == it.forType.packageName
        && resolved.declaration.simpleName.asString() == it.forType.simpleName }
}

fun KSPropertyDeclaration.returnTypeIn(values: List<ResolvedViewModel.WrappedType>): Boolean {
    return getWrappedTypeFrom(values) != null
}

@JvmName("returnTypeIsInScoped")
fun KSPropertyDeclaration.returnTypeIn(values: List<ResolvedViewModel.ScopedType>): Boolean {
    val resolved = type.resolve()
    return values.any { resolved.declaration.packageName.asString() == it.type.packageName
        && resolved.declaration.simpleName.asString() == it.type.simpleName }
}