package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.*

class WrappedPropertyImplMapper(private val wrapper: ClassName) : Mapper<KSPropertyDeclaration, FunSpec> {
    override fun invoke(property: KSPropertyDeclaration): FunSpec = with(property) {
        return@with FunSpec.builder(property.simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("block", LambdaTypeName.get(receiver = wrapper, returnType = UNIT))
            .addStatement("origin.${property.simpleName.asString()}.apply(block)")
            .build()
    }
}