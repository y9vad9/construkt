package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier

object ImplementationPropertyMapper : Mapper<KSPropertyDeclaration, FunSpec> {
    override fun invoke(p1: KSPropertyDeclaration): FunSpec = with(p1) {
        return FunSpec.builder(simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(toParameterSpec())
            .addCode(
                """
                origin.${simpleName.asString()}(value)
            """.trimIndent()
            )
            .build()
    }
}