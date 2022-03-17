package com.construkt.codegen

import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

class ExtensionsGenerator(
    private val interfaceName: ClassName,
    private val functions: List<KSFunctionDeclaration>,
    private val properties: List<KSPropertyDeclaration>
) : CodeGenerator<List<FunSpec>> {
    private fun functionOf(function: KSFunctionDeclaration, annotated: Boolean = false, index: Int = 0): FunSpec {
        val parametersCall = function.parameters.joinToString("\n") { parameter ->
            parameter.name!!.asString() + ".collect { ${function.simpleName.asString().formatFunctionName()}(${
                function.parameters.joinToString(
                    ", "
                ) { it.name!!.asString().plus(".value") }
            }) }"
        }
        return FunSpec.builder(function.simpleName.asString().formatFunctionName())
            .receiver(interfaceName)
            .apply {
                if (annotated)
                    addAnnotation(
                        AnnotationSpec.builder(JvmName::class)
                            .addMember("\"${function.simpleName.asString().formatFunctionName()}$index\"").build()
                    )
            }
            .addParameters(
                function.parameters.map(KSValueParameter::toParameterSpec)
                    .map {
                        it.toBuilder(
                            type = ClassName("com.construct", "State").parameterizedBy(it.type)
                        ).build()
                    }
            ).addCode(
                """
                    ${
                    function.simpleName.asString().formatFunctionName()
                }(${function.parameters.joinToString(", ") { it.name!!.asString().plus(".value") }})
                    lifecycleOwner.lifecycleScope.launch {
                        $parametersCall
                    }
                """.trimIndent()
            ).build()
    }

    private fun functionOf(property: KSPropertyDeclaration): FunSpec {
        return FunSpec.builder(property.simpleName.asString().formatFunctionName())
            .receiver(interfaceName)
            .addModifiers(KModifier.ABSTRACT)
            .addParameter(property.toParameterSpec())
            .addCode(
                """
                ${property.simpleName.asString().formatFunctionName()}(value.value)
                lifecycleOwner.lifecycleScope.launch {
                    value.collect {
                        ${property.simpleName.asString().formatFunctionName()}(it)
                    }
                }
            """.trimIndent()
            )
            .build()
    }

    override fun generate(): List<FunSpec> {
        return functions
            .groupBy { it.parameters.size to it.simpleName.asString() }
            .map {
                when (it.value.size) {
                    1 -> listOf(functionOf(it.value[0]))
                    else -> it.value.mapIndexed { index, declaration -> functionOf(declaration, true, index) }
                }
            }.flatten()
    }
}