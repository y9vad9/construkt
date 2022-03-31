package com.construkt.codegen

import com.construkt.models.LayoutParamsModel
import com.construkt.models.ResolvedViewModel
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec

class CompoundCodeGenerator(
    private val model: ResolvedViewModel,
    isViewGroup: Boolean,
    layoutParams: LayoutParamsModel,
    functions: List<KSFunctionDeclaration>,
    properties: List<KSPropertyDeclaration>,
) : CodeGenerator<FileSpec> {

    private val interfaceName = ClassName(model.viewType.packageName, model.viewType.simpleName.plus("Scope"))
    private val implementationName = ClassName(model.viewType.packageName, model.viewType.simpleName.plus("ScopeImpl"))

    private val dslFunsGenerator = DSLFunctionsGenerator(
        layoutParams.name,
        interfaceName,
        implementationName,
        model.viewType,
        model.viewType.simpleName.replaceFirstChar { it.lowercase() }
    )

    private val interfaceCodeGenerator = InterfaceGenerator(
        interfaceName,
        model,
        functions,
        properties,
        isViewGroup
    )

    private val implementationCodeGenerator = ImplementationGeneration(
        implementationName,
        interfaceName,
        model,
        functions,
        properties,
        isViewGroup
    )

    private val extensionCodeGenerator = ExtensionsGenerator(
        interfaceName, functions, properties
    )

//    private val layoutParamsExtensionsGenerator: LayoutParamsExtensionsGenerator = LayoutParamsExtensionsGenerator(
//        layoutParams.name, layoutParams.functions, layoutParams.properties
//    )

    override fun generate(): FileSpec {
        return FileSpec.builder(model.viewType.packageName, model.viewType.simpleName)
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