package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier

object ImplementationFunctionMapper : Mapper<KSFunctionDeclaration, FunSpec> {
    override fun invoke(declaration: KSFunctionDeclaration): FunSpec = with(declaration) {
        val parameters = parameters.joinToString(", ") { parameter ->
            parameter.name!!.asString().let {
                if (parameter.isVararg)
                    "*$it"
                else it
            }
        }
        return FunSpec.builder(simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.OVERRIDE)
            .addParameters(
                declaration.parameters.map(KSValueParameter::toParameterSpec)
            ).addCode(
                """
                origin.${simpleName.asString()}($parameters)
            """.trimIndent()
            ).build()
    }
}