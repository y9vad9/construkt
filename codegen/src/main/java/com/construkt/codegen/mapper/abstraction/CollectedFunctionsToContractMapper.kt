package com.construkt.codegen.mapper.abstraction

import com.construkt.codegen.mapper.Mapper
import com.construkt.matching.isMenuFunction
import com.construkt.matching.isSetFunction
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FunSpec

object CollectedFunctionsToContractMapper : Mapper<KSFunctionDeclaration, FunSpec?> {
    override fun invoke(function: KSFunctionDeclaration): FunSpec? = with(function) {
        return@with when {
            isMenuFunction() -> MenuFunctionToAbstractMapper(function)
            isSetFunction() -> FunctionToAbstractMapper(function)
            else -> null
        }
    }
}