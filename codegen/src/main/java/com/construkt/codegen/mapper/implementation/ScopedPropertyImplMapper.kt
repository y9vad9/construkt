package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.ksp.toTypeName

object ScopedPropertyImplMapper : Mapper<KSPropertyDeclaration, FunSpec> {
    override fun invoke(property: KSPropertyDeclaration): FunSpec = with(property) {
        return@with FunSpec.builder(property.simpleName.asString().formatFunctionName())
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("block", LambdaTypeName.get(receiver = property.type.toTypeName(), returnType = UNIT))
            .addStatement("origin.${simpleName.asString()}.apply(block)")
            .build()
    }
}