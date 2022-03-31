package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.*

class WrappedTypeFunctionImplMapper(private val wrapper: ClassName) : Mapper<KSFunctionDeclaration, FunSpec> {
    override fun invoke(function: KSFunctionDeclaration): FunSpec = with(function) {
        val parametersEnumeration = function.parameters.joinToString(", ") { it.name!!.asString() }
        return@with FunSpec.builder(simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.OVERRIDE)
            .addParameters(parameters.map(KSValueParameter::toParameterSpec))
            .addParameter("block", LambdaTypeName.get(receiver = wrapper, returnType = UNIT))
            .addStatement("val result = origin.${simpleName.asString()}($parametersEnumeration)")
            .addStatement("%T(result).apply(block)", wrapper)
            .build()
    }
}