package com.construct.internal.extensions

import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toTypeName

@OptIn(KotlinPoetKspPreview::class)
internal fun KSPropertyDeclaration.toParameterSpec(): ParameterSpec {
    return ParameterSpec.builder("value", type.toTypeName()).build()
}