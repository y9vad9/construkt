package com.construkt.codegen

import com.construkt.codegen.mapper.abstraction.CollectedFunctionsToContractMapper
import com.construkt.codegen.mapper.abstraction.CollectedPropertyToContractMapper
import com.construkt.models.ResolvedViewModel
import com.construkt.types.Builtins
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec

class InterfaceGenerator(
    private val className: ClassName,
    private val model: ResolvedViewModel,
    private val functions: List<KSFunctionDeclaration>,
    private val properties: List<KSPropertyDeclaration>,
    private val isViewGroup: Boolean
) : CodeGenerator<TypeSpec> {
    override fun generate(): TypeSpec {
        return TypeSpec.interfaceBuilder(className)
            .addSuperinterface(
                if (isViewGroup) Builtins.ViewGroupScope(model.viewType) else Builtins.ViewScope(model.viewType)
            )
            .addFunctions(functions.mapNotNull(CollectedFunctionsToContractMapper(model.wrapped, model.scoped)))
            .addFunctions(properties.mapNotNull(CollectedPropertyToContractMapper(model.scoped, model.wrapped)))
            .build()
    }
}