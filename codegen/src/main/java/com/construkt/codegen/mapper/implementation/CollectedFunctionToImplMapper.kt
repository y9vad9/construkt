package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.matching.isMenuFunction
import com.construkt.matching.isSetFunction
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FunSpec

object CollectedFunctionToImplMapper : Mapper<KSFunctionDeclaration, FunSpec?> {
    override fun invoke(function: KSFunctionDeclaration): FunSpec? = with(function) {
        return@with when {
            isMenuFunction() -> MenuFunctionToDSLMapper(function)
            isSetFunction() -> ImplementationFunctionMapper(function)
            else -> null
        }
    }
}