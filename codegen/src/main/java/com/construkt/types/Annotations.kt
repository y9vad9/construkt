package com.construkt.types

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName

object Annotations {
    val InternalConstruktApi = ClassName("com.construkt.annotation", "InternalConstruktApi")
    fun JvmName(name: String): AnnotationSpec =
        AnnotationSpec.builder(JvmName::class)
            .addMember("\"$name\"").build()
}