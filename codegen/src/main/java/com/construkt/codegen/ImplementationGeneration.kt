package com.construkt.codegen

import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.*

class ImplementationGeneration(
    private val className: ClassName,
    private val interfaceName: ClassName,
    private val origin: ClassName,
    private val functions: List<KSFunctionDeclaration>,
    private val properties: List<KSPropertyDeclaration>,
    private val isViewGroup: Boolean
) : CodeGenerator<TypeSpec> {

    private fun functionOf(function: KSFunctionDeclaration): FunSpec {
        val parameters = function.parameters.joinToString(", ") { parameter ->
            parameter.name!!.asString().let {
                if (parameter.isVararg)
                    "*$it"
                else it
            }
        }
        return FunSpec.builder(function.simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.OVERRIDE)
            .addParameters(
                function.parameters.map(KSValueParameter::toParameterSpec)
            ).addCode(
                """
                origin.${function.simpleName.asString()}($parameters)
            """.trimIndent()
            ).build()
    }

    private fun functionOf(property: KSPropertyDeclaration): FunSpec {
        return FunSpec.builder(property.simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(property.toParameterSpec())
            .addCode(
                """
                origin.${property.simpleName.asString()}(value)
            """.trimIndent()
            )
            .build()
    }

    private fun TypeSpec.Builder.applyParameters(): TypeSpec.Builder {
        addProperties(
            listOf(
                PropertySpec.builder("context", ClassName("android.content", "Context")).initializer("context"),
                PropertySpec.builder("lifecycleOwner", ClassName("androidx.lifecycle", "LifecycleOwner"))
                    .initializer("lifecycleOwner")
            ).map { it.addModifiers(KModifier.OVERRIDE).build() }
        )
        addProperty(PropertySpec.builder("origin", origin).initializer("origin").build())
        primaryConstructor(
            FunSpec.constructorBuilder().addParameters(
                listOf(
                    ParameterSpec.builder("context", ClassName("android.content", "Context")).build(),
                    ParameterSpec.builder("origin", origin).build(),
                    ParameterSpec.builder("lifecycleOwner", ClassName("androidx.lifecycle", "LifecycleOwner")).build()
                )
            ).build()
        )
        return this
    }

    private fun TypeSpec.Builder.applyAddViewIfPossible(): TypeSpec.Builder = apply {
        if (isViewGroup) {
            addFunction(
                FunSpec.builder("addView")
                    .addAnnotation(ClassName("com.construkt.annotation", "InternalConstructApi"))
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter("view", ClassName("android.view", "View"))
                    .addCode(
                        """
                    origin.addView(view)
                """.trimIndent()
                    )
                    .build()
            )
            addFunction(
                FunSpec.builder("removeViewView")
                    .addAnnotation(ClassName("com.construkt.annotation", "InternalConstructApi"))
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter("view", ClassName("android.view", "View"))
                    .addCode(
                        """
                    origin.removeView(view)
                """.trimIndent()
                    )
                    .build()
            )
        }
    }

    override fun generate(): TypeSpec {
        return TypeSpec.classBuilder(className.simpleName)
            .addModifiers(KModifier.PRIVATE)
            .addSuperinterface(interfaceName)
            .addFunctions(functions.map(::functionOf))
            .addFunctions(properties.map(::functionOf))
            .applyParameters()
            .applyAddViewIfPossible()
            .build()
    }
}