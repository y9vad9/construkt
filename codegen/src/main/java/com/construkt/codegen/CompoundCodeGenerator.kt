package com.construkt.codegen

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec

class CompoundCodeGenerator(
    private val viewType: ClassName,
    isViewGroup: Boolean,
    layoutParams: ClassName,
    functions: List<KSFunctionDeclaration>,
    properties: List<KSPropertyDeclaration>
) : CodeGenerator<FileSpec> {
    private val annotationName = "${viewType.simpleName}DSL"
    private val interfaceName = ClassName(viewType.packageName, viewType.simpleName.plus("Scope"))
    private val implementationName = ClassName(viewType.packageName, viewType.simpleName.plus("ScopeImpl"))

    private val dslFunsGenerator = DSLFunctionsGenerator(
        layoutParams,
        interfaceName,
        implementationName,
        viewType,
        viewType.simpleName.replaceFirstChar { it.lowercase() },
        annotationName
    )

    private val interfaceCodeGenerator = InterfaceGenerator(
        interfaceName,
        viewType,
        functions,
        properties,
        isViewGroup
    )

    private val implementationCodeGenerator = ImplementationGeneration(
        implementationName,
        interfaceName,
        viewType,
        functions,
        properties,
        isViewGroup
    )

    private val extensionCodeGenerator = ExtensionsGenerator(
        interfaceName, functions, properties
    )

    override fun generate(): FileSpec {
        return FileSpec.builder(viewType.packageName, viewType.simpleName)
            .addImport("androidx.lifecycle", "lifecycleScope")
            .addImport("kotlinx.coroutines", "launch")
            .addImport("com.construkt.builtins", "menuDsl")
            .addType(interfaceCodeGenerator.generate())
            .addType(implementationCodeGenerator.generate())
            .apply {
                (extensionCodeGenerator.generate() + dslFunsGenerator.generate()).forEach { addFunction(it) }
            }
            .build()
    }

}