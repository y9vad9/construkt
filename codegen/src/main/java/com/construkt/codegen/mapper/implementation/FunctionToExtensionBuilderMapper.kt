package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec

object FunctionToExtensionBuilderMapper : Mapper<FunctionToExtensionBuilderMapper.Data, FunSpec> {
    class Data(val className: ClassName, val function: KSFunctionDeclaration)

    override fun invoke(data: Data): FunSpec = with(data.function) {
        val parameterSequence = parameters.joinToString(", ") { it.name!!.asString() }
        return@with FunSpec.builder(simpleName.asString().formatFunctionName())
            .receiver(data.className)
            .addParameters(parameters.map(KSValueParameter::toParameterSpec))
            .addStatement("${simpleName.asString()}($parameterSequence)")
            .addStatement("return this")
            .returns(data.className)
            .build()
    }
}