package com.construkt.codegen

import com.construkt.internal.extensions.addFunctions
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec

class LayoutParamsFileGenerator(
    private val functions: List<FunSpec>,
    private val destination: ClassName = ClassName("com.construkt.generated.layoutParams", "extensions")
) : CodeGenerator<FileSpec> {
    override fun generate(): FileSpec {
        return FileSpec.builder(destination.packageName, destination.simpleName)
            .addFunctions(functions)
            .build()
    }

}