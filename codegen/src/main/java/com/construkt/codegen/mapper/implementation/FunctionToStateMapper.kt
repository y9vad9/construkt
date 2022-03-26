package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.construkt.matching.isSetFunction
import com.construkt.types.Annotations
import com.construkt.types.Builtins
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ksp.toTypeName

object FunctionToStateMapper : Mapper<FunctionToStateMapper.Data, FunSpec?> {
    class Data(val function: KSFunctionDeclaration, val index: Int, val receiver: ClassName)
    override fun invoke(data: Data): FunSpec? = with(data.function) {
        if(!isSetFunction()) return@with null
        val functionName = simpleName.asString().formatFunctionName()
        val parameters = parameters.map {
            ParameterSpec.builder(it.name!!.asString(), Builtins.State(it.type.resolve().toTypeName())).build()
        }
        val parametersSequence = parameters.joinToString(", ") { it.name + ".value" }
        return@with FunSpec.builder(functionName)
            .receiver(data.receiver)
            .addParameters(parameters)
            .addAnnotation(Annotations.JvmName("$functionName${data.index}"))
            .addCode(
                CodeBlock.builder()
                    .beginControlFlow("lifecycleOwner.lifecycleScope.launch")
                    .apply {
                        parameters.forEach {
                            beginControlFlow("launch")
                            addStatement("${it.name}.collect { $functionName($parametersSequence) }")
                            endControlFlow()
                        }
                    }
                    .endControlFlow()
                    .addStatement("$functionName($parametersSequence)")
                    .build()
            )
            .build()
        TODO()
    }
}