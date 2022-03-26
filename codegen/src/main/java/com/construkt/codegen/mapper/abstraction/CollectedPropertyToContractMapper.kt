package com.construkt.codegen.mapper.abstraction

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.construkt.matching.isMenuProperty
import com.construkt.matching.isMutable
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier

object CollectedPropertyToContractMapper : Mapper<KSPropertyDeclaration, FunSpec?> {
    override fun invoke(p1: KSPropertyDeclaration): FunSpec? = with(p1) {
        return@with when {
            isMenuProperty() -> MenuPropertyToAbstractMapper(this)
            isMutable() -> FunSpec.builder(simpleName.asString().formatFunctionName())
                .addModifiers(KModifier.ABSTRACT)
                .addParameter(toParameterSpec())
                .build()
            else -> null
        }
    }
}