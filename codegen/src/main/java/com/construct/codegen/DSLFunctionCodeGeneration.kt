package com.construct.codegen

import com.construct.ViewScope
import com.construct.descriptions.Type
import com.construct.descriptions.toClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.asClassName

class DSLFunctionCodeGeneration(
    private val functionName: String,
    private val viewType: Type,
    private val implementationName: String,
    private val builderType: Type
) : CodeGenerator<FunSpec> {
    override fun generate(): FunSpec {
        return FunSpec.builder(functionName)
            .receiver(ViewScope::class.asClassName())
            .addParameter(
                "builder",
                LambdaTypeName.get(receiver = builderType.toClassName(), returnType = Unit::class.asClassName())
            )
            .addCode("""
                addView(${viewType.packageName}.${viewType.name}(context).also { $implementationName(it) })
            """.trimIndent())
            .build()
    }
}