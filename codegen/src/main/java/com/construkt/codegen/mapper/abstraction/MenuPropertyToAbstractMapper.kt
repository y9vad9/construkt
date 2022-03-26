package com.construkt.codegen.mapper.abstraction

import com.construkt.codegen.mapper.Mapper
import com.construkt.types.Annotations
import com.construkt.types.Builtins
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.UNIT

object MenuPropertyToAbstractMapper : Mapper<KSPropertyDeclaration, FunSpec> {
    override fun invoke(p1: KSPropertyDeclaration): FunSpec = with(p1) {
        return@with FunSpec.builder(simpleName.asString())
            .addModifiers(KModifier.ABSTRACT)
            .addParameter("block", LambdaTypeName.get(receiver = Builtins.MenuDSL, returnType = UNIT))
            .build()
    }

}