package com.construkt.internal.extensions

import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toTypeName

@OptIn(KotlinPoetKspPreview::class)
internal fun KSValueParameter.toParameterSpec(): ParameterSpec =
    ParameterSpec.builder(name?.asString()!!, type.toTypeName()).build()