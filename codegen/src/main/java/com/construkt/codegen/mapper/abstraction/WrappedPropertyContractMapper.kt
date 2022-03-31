package com.construkt.codegen.mapper.abstraction

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.*

class WrappedPropertyContractMapper(private val wrapper: ClassName) : Mapper<KSPropertyDeclaration, FunSpec> {
    override fun invoke(property: KSPropertyDeclaration): FunSpec = with(property) {
        return@with FunSpec.builder(property.simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.ABSTRACT)
            .addParameter("block", LambdaTypeName.get(receiver = wrapper, returnType = UNIT))
            .build()
    }
}