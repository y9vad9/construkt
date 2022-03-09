package com.construct.codegen

import com.construct.descriptions.Func
import com.construct.descriptions.Property
import com.construct.descriptions.Type
import com.squareup.kotlinpoet.FileSpec

class CompoundCodeGenerator(
    private val viewType: Type,
    functions: List<Func>,
    properties: List<Property>
) : CodeGenerator<FileSpec> {

    private val funs = functions + properties.map { Func(it.name, listOf(it.type), it.type) } // then it will be anyway mapped into properties back

    private val dslFunGenerator = DSLFunctionCodeGeneration(
        viewType.name.replaceFirstChar { it.lowercase() },
        viewType, viewType.name.plus("ScopeImpl"),
        Type(viewType.packageName, viewType.name.plus("Scope"), emptyList())
    )

    private val interfaceCodeGenerator = InterfaceCodeGenerator(
        viewType.name.plus("Scope"),
        funs
    )

    private val implementationCodeGenerator = ImplementationClassCodeGenerator(
        viewType.name.plus("ScopeImpl"),
        funs,
        viewType
    )

    private val extensionCodeGenerator = ExtensionsCodeGenerator(
        viewType.name.plus("Scope"),
        viewType.packageName,
        funs,
        Type(viewType.packageName, viewType.name.plus("DSL"), listOf())
    )

    override fun generate(): FileSpec {
        return FileSpec.builder(viewType.packageName, viewType.name)
            .addType(interfaceCodeGenerator.generate())
            .addType(implementationCodeGenerator.generate())
            .apply {
                val extensions = extensionCodeGenerator.generate()
                extensions.first.forEach { addProperty(it) }
                extensions.second.forEach { addFunction(it) }
            }
            .addFunction(dslFunGenerator.generate())
            .build()
    }

}