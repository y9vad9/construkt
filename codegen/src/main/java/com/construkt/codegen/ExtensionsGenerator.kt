package com.construkt.codegen

import com.construkt.codegen.mapper.implementation.PropertyToStateMapper
import com.construkt.codegen.mapper.implementation.FunctionToStateMapper
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec

class ExtensionsGenerator(
    private val interfaceName: ClassName,
    private val functions: List<KSFunctionDeclaration>,
    private val properties: List<KSPropertyDeclaration>
) : CodeGenerator<List<FunSpec>> {
    override fun generate(): List<FunSpec> {
        return functions
            .groupBy { it.parameters.size to it.simpleName.asString() }
            .mapNotNull {
                when (it.value.size) {
                    1 -> listOf(FunctionToStateMapper(FunctionToStateMapper.Data(it.value[0], 0, interfaceName)))
                    else -> List(it.value.size) { index ->
                        FunctionToStateMapper(FunctionToStateMapper.Data(it.value[index], index, interfaceName))
                    }
                }
            }.flatten().filterNotNull() + properties.map { PropertyToStateMapper(PropertyToStateMapper.Data(it, interfaceName)) }
    }
}