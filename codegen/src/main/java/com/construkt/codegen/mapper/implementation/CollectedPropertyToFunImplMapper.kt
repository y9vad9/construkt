package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.matching.isMenuProperty
import com.construkt.matching.isMutable
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.FunSpec

object CollectedPropertyToFunImplMapper : Mapper<KSPropertyDeclaration, FunSpec?> {
    override fun invoke(property: KSPropertyDeclaration): FunSpec? = with(property) {
        when {
            isMenuProperty() -> MenuPropertyToDSLMapper(property)
            isMutable() -> ImplementationPropertyMapper(property)
            else -> null
        }
    }
}