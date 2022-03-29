package com.construkt.codegen

import com.construkt.codegen.mapper.implementation.FunctionToExtensionBuilderMapper
import com.construkt.codegen.mapper.implementation.LayoutParamsFunctionBuilderMapper
import com.construkt.codegen.mapper.implementation.PropertyToExtensionFunctionBuilderMapper
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec

class LayoutParamsExtensionsGenerator(
    private val className: ClassName,
    private val functions: List<KSFunctionDeclaration>,
    private val properties: List<KSPropertyDeclaration>
) : CodeGenerator<List<FunSpec>> {

    override fun generate(): List<FunSpec> {
        return functions.map {
            FunctionToExtensionBuilderMapper(FunctionToExtensionBuilderMapper.Data(className, it))
        } + properties.map {
            PropertyToExtensionFunctionBuilderMapper(PropertyToExtensionFunctionBuilderMapper.Data(className, it))
        } + LayoutParamsFunctionBuilderMapper(className)
    }
}