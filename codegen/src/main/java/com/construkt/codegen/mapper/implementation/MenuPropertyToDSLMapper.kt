package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.types.Annotations
import com.construkt.types.Builtins
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.UNIT

object MenuPropertyToDSLMapper : Mapper<KSPropertyDeclaration, FunSpec> {
    override fun invoke(input: KSPropertyDeclaration): FunSpec {
        return FunSpec.builder(input.simpleName.asString())
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("block", LambdaTypeName.get(receiver = Builtins.MenuDSL, returnType = UNIT))
            .addCode("origin.${input.simpleName.asString()}.menuDsl().apply(block)")
            .build()
    }
}