package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.matching.getWrappedWithReturnType
import com.construkt.matching.isMenuFunction
import com.construkt.matching.isSetFunction
import com.construkt.matching.returnTypeIn
import com.construkt.models.ResolvedViewModel
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FunSpec

class CollectedFunctionToImplMapper(
    private val wrapped: List<ResolvedViewModel.WrappedType>,
    private val scoped: List<ResolvedViewModel.ScopedType>
) : Mapper<KSFunctionDeclaration, FunSpec?> {
    override fun invoke(function: KSFunctionDeclaration): FunSpec? = with(function) {
        return@with when {
            isMenuFunction() -> MenuFunctionToDSLMapper(function)
            isSetFunction() -> ImplementationFunctionMapper(function)
            returnTypeIn(wrapped) -> WrappedTypeFunctionImplMapper(getWrappedWithReturnType(wrapped)!!.using)(function)
            returnTypeIn(scoped) -> ScopedFunctionImplMapper(function)
            else -> null
        }
    }
}