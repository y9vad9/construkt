package com.construkt.models

import com.squareup.kotlinpoet.ClassName

class ResolvedViewModel(
    val viewType: ClassName,
    val wrapped: List<WrappedType>,
    val scoped: List<ScopedType>
) {
    class WrappedType(val forType: ClassName, val using: ClassName)
    @JvmInline
    value class ScopedType(val type: ClassName)
}