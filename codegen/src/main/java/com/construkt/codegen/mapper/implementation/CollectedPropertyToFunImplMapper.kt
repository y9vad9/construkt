package com.construkt.codegen.mapper.implementation

import com.construkt.codegen.mapper.Mapper
import com.construkt.codegen.mapper.abstraction.ScopedPropertyContractMapper
import com.construkt.codegen.mapper.abstraction.WrappedPropertyContractMapper
import com.construkt.matching.getWrappedTypeFrom
import com.construkt.matching.isMenuProperty
import com.construkt.matching.isMutable
import com.construkt.matching.returnTypeIn
import com.construkt.models.ResolvedViewModel
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.FunSpec

class CollectedPropertyToFunImplMapper(
    private val scoped: List<ResolvedViewModel.ScopedType>,
    private val wrapped: List<ResolvedViewModel.WrappedType>
) : Mapper<KSPropertyDeclaration, FunSpec?> {
    override fun invoke(property: KSPropertyDeclaration): FunSpec? = with(property) {
        when {
            isMenuProperty() -> MenuPropertyToDSLMapper(property)
            isMutable() -> ImplementationPropertyMapper(property)
            returnTypeIn(scoped) -> ScopedPropertyImplMapper(this)
            returnTypeIn(wrapped) -> WrappedPropertyImplMapper(getWrappedTypeFrom(wrapped)!!.using)(this)
            else -> null
        }
    }
}