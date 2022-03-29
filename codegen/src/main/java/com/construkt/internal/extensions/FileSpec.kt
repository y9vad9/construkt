package com.construkt.internal.extensions

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec

fun FileSpec.Builder.addFunctions(functions: Iterable<FunSpec>): FileSpec.Builder = apply {
    functions.forEach { addFunction(it) }
}