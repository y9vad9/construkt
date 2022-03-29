package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ksp.toTypeName

object PropertyToExtensionFunctionBuilderMapper : Mapper<PropertyToExtensionFunctionBuilderMapper.Data, FunSpec> {
    class Data(val className: ClassName, val function: KSPropertyDeclaration)

    override fun invoke(data: Data): FunSpec = with(data.function) {
        return@with FunSpec.builder(simpleName.asString().formatFunctionName())
            .receiver(data.className)
            .addParameter("value", type.toTypeName())
            .addStatement("${simpleName.asString()} = value")
            .addStatement("return this")
            .returns(data.className)
            .build()
    }

}