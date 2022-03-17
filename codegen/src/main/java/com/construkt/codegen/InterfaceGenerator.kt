package com.construkt.codegen

import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec

class InterfaceGenerator(
    private val className: ClassName,
    private val functions: List<KSFunctionDeclaration>,
    private val properties: List<KSPropertyDeclaration>
) : CodeGenerator<TypeSpec> {
    private fun functionOf(function: KSFunctionDeclaration): FunSpec {
        return FunSpec.builder(function.simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.ABSTRACT)
            .addParameters(function.parameters.map(KSValueParameter::toParameterSpec))
            .build()
    }

    private fun functionOf(property: KSPropertyDeclaration): FunSpec {
        return FunSpec.builder(property.simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.ABSTRACT)
            .addParameter(property.toParameterSpec())
            .build()
    }

    override fun generate(): TypeSpec {
        return TypeSpec.interfaceBuilder(className)
            .addSuperinterface(ClassName("com.construct", "ViewScope"))
            .addFunctions(functions.map(::functionOf))
            .addFunctions(properties.map(::functionOf))
            .build()
    }
}