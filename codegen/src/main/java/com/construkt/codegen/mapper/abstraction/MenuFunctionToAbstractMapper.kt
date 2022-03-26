package com.construkt.codegen.mapper.abstraction

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.construkt.types.Builtins
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.UNIT

object MenuFunctionToAbstractMapper : Mapper<KSFunctionDeclaration, FunSpec> {
    override fun invoke(p1: KSFunctionDeclaration): FunSpec = with(p1) {
        return@with FunSpec.builder(simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.ABSTRACT)
            .addParameters(p1.parameters.map(KSValueParameter::toParameterSpec))
            .addParameter("block", LambdaTypeName.get(receiver = Builtins.MenuDSL, returnType = UNIT))
            .build()
    }
}