package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec

object LayoutParamsFunctionBuilderMapper : Mapper<ClassName, FunSpec> {
    override fun invoke(name: ClassName): FunSpec = with(name) {
        return@with FunSpec.builder(
            name.simpleNames.subList(0, name.simpleNames.size)
                .joinToString("")
                .replace("Layout", "")
                .replace("Params", "")
                .replace(".", "")
                .replace("ViewGroup", "")
                .plus("LayoutParams")
                .replaceFirstChar { it.lowercase() }
        )
            .addStatement("return %T(-2, -2)", name)
            .returns(name)
            .build()
    }

}