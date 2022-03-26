package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier

object PropertyToStateMapper : Mapper<PropertyToStateMapper.Data, FunSpec> {
    class Data(val property: KSPropertyDeclaration, val interfaceName: ClassName)

    override fun invoke(data: Data): FunSpec = with(data) {
        return@with FunSpec.builder(property.simpleName.asString().formatFunctionName())
            .receiver(interfaceName)
            .addModifiers(KModifier.ABSTRACT)
            .addParameter(property.toParameterSpec())
            .addCode(
                """
                ${property.simpleName.asString().formatFunctionName()}(value.value)
                lifecycleOwner.lifecycleScope.launch {
                    value.collect {
                        ${property.simpleName.asString()} = it
                    }
                }
            """.trimIndent()
            )
            .build()
    }
}