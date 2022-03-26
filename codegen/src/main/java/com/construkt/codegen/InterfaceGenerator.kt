package com.construkt.codegen

import com.construkt.codegen.mapper.abstraction.CollectedFunctionsToContractMapper
import com.construkt.codegen.mapper.abstraction.CollectedPropertyToContractMapper
import com.construkt.types.Builtins
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.*

class InterfaceGenerator(
    private val className: ClassName,
    private val viewType: ClassName,
    private val functions: List<KSFunctionDeclaration>,
    private val properties: List<KSPropertyDeclaration>,
    private val isViewGroup: Boolean
) : CodeGenerator<TypeSpec> {
    override fun generate(): TypeSpec {
        return TypeSpec.interfaceBuilder(className)
            .addSuperinterface(
                if(isViewGroup) Builtins.ViewGroupScope(viewType) else Builtins.ViewScope(viewType)
            )
            .addFunctions(functions.mapNotNull(CollectedFunctionsToContractMapper))
            .addFunctions(properties.mapNotNull(CollectedPropertyToContractMapper))
            .build()
    }
}