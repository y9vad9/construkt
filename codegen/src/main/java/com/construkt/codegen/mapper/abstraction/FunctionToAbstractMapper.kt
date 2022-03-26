package com.construkt.codegen.mapper.abstraction

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier

object FunctionToAbstractMapper : Mapper<KSFunctionDeclaration, FunSpec> {
    override fun invoke(p1: KSFunctionDeclaration): FunSpec = with(p1) {
        return@with FunSpec.builder(simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.ABSTRACT)
            .addParameters(parameters.map(KSValueParameter::toParameterSpec))
            .build()
    }
}