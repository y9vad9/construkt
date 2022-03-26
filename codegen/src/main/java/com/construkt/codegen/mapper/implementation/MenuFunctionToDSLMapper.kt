package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.construkt.types.Annotations
import com.construkt.types.Builtins
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.UNIT

object MenuFunctionToDSLMapper : Mapper<KSFunctionDeclaration, FunSpec> {
    override fun invoke(input: KSFunctionDeclaration): FunSpec {
        val parameters = input.parameters.joinToString(", ") { it.name?.asString() ?: "value" }
        return FunSpec.builder(input.simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.OVERRIDE)
            .addParameters(input.parameters.map { it.toParameterSpec() })
            .addParameter("block", LambdaTypeName.get(receiver = Builtins.MenuDSL, returnType = UNIT))
            .addCode("origin.${input.simpleName.asString()}($parameters).menuDsl().apply(block)")
            .build()
    }
}