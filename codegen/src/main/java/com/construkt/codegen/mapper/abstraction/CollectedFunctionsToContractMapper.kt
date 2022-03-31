package com.construkt.codegen.mapper.abstraction

import com.construkt.codegen.mapper.Mapper
import com.construkt.matching.getWrappedWithReturnType
import com.construkt.matching.isMenuFunction
import com.construkt.matching.isSetFunction
import com.construkt.matching.returnTypeIn
import com.construkt.models.ResolvedViewModel
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FunSpec

class CollectedFunctionsToContractMapper(
    private val wrapped: List<ResolvedViewModel.WrappedType>,
    private val scoped: List<ResolvedViewModel.ScopedType>
) : Mapper<KSFunctionDeclaration, FunSpec?> {
    override fun invoke(function: KSFunctionDeclaration): FunSpec? = with(function) {
        return@with when {
            isMenuFunction() -> MenuFunctionToAbstractMapper(function)
            isSetFunction() -> FunctionToAbstractMapper(function)
            returnTypeIn(scoped) -> ScopedFunctionContractMapper(function)
            returnTypeIn(wrapped) -> WrappedTypeFunctionContractMapper(getWrappedWithReturnType(wrapped)!!.using)(function)
            else -> null
        }
    }
}