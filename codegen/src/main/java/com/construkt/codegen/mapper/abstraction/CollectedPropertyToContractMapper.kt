package com.construkt.codegen.mapper.abstraction

import com.construkt.codegen.mapper.Mapper
import com.construkt.internal.extensions.formatFunctionName
import com.construkt.internal.extensions.toParameterSpec
import com.construkt.matching.getWrappedTypeFrom
import com.construkt.matching.isMenuProperty
import com.construkt.matching.isMutable
import com.construkt.matching.returnTypeIn
import com.construkt.models.ResolvedViewModel
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier

class CollectedPropertyToContractMapper(
    private val scoped: List<ResolvedViewModel.ScopedType>,
    private val wrapped: List<ResolvedViewModel.WrappedType>
) : Mapper<KSPropertyDeclaration, FunSpec?> {
    override fun invoke(p1: KSPropertyDeclaration): FunSpec? = with(p1) {
        return@with when {
            isMenuProperty() -> MenuPropertyToAbstractMapper(this)
            isMutable() -> FunSpec.builder(simpleName.asString().formatFunctionName())
                .addModifiers(KModifier.ABSTRACT)
                .addParameter(toParameterSpec())
                .build()
            returnTypeIn(scoped) -> ScopedPropertyContractMapper(this)
            returnTypeIn(wrapped) -> WrappedPropertyContractMapper(getWrappedTypeFrom(wrapped)!!.using)(this)
            else -> null
        }
    }
}