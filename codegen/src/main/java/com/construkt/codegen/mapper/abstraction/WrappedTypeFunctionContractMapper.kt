package com.construkt.codegen.mapper.abstraction

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.*

class WrappedTypeFunctionContractMapper(private val wrapper: ClassName) : Mapper<KSFunctionDeclaration, FunSpec> {
    override fun invoke(function: KSFunctionDeclaration): FunSpec = with(function) {
        return@with FunSpec.builder(simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.ABSTRACT)
            .addParameters(parameters.map(KSValueParameter::toParameterSpec))
            .addParameter("block", LambdaTypeName.get(receiver = wrapper, returnType = UNIT))
            .build()
    }
}