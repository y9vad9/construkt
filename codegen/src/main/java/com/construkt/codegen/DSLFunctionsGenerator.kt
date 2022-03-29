package com.construkt.codegen

import com.construkt.types.Builtins
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

class DSLFunctionsGenerator(
    private val layoutParams: ClassName,
    private val interfaceName: ClassName,
    private val implementation: ClassName,
    private val origin: ClassName,
    private val functionName: String
) : CodeGenerator<List<FunSpec>> {

    private fun function(): FunSpec {
        return FunSpec.builder(functionName)
            .receiver(Builtins.ViewGroupScope(STAR))
            .addParameter(
                ParameterSpec.builder("layoutParams", layoutParams.copy(nullable = true)).defaultValue("null").build()
            )
            .addParameter(
                "builder",
                LambdaTypeName.get(receiver = interfaceName, returnType = Unit::class.asClassName())
            )
            .addCode(
                """
                val origin = ${origin.simpleName}(context)
                layoutParams?.let { origin.layoutParams = it }
                val implementation = $implementation(origin.context, origin, lifecycleOwner)
                addView(origin)
                builder(implementation)
            """.trimIndent()
            )
            .build()
    }

    private fun functionStated(): FunSpec {
        return FunSpec.builder(functionName)
            .receiver(ClassName("com.construkt", "ViewGroupScope").parameterizedBy(STAR))
            .addParameter("layoutParams", ClassName("com.construkt", "State").parameterizedBy(layoutParams))
            .addParameter(
                "builder",
                LambdaTypeName.get(receiver = interfaceName, returnType = Unit::class.asClassName())
            )
            .addCode(
                """
                val origin = ${origin.simpleName}(context)
                origin.layoutParams = layoutParams.value
                lifecycleOwner.lifecycleScope.launch {
                    layoutParams.collect { origin.layoutParams = it }
                }
                val implementation = $implementation(origin.context, origin, lifecycleOwner)
                addView(origin)
                builder(implementation)
            """.trimIndent()
            )
            .build()
    }

    override fun generate(): List<FunSpec> {
        return listOf(function(), functionStated())
    }
}