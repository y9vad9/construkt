package com.construkt.models

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ClassName

class LayoutParamsModel(
    val name: ClassName,
    val functions: List<KSFunctionDeclaration>,
    val properties: List<KSPropertyDeclaration>
)