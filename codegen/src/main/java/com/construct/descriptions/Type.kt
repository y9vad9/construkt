package com.construct.descriptions

import com.squareup.kotlinpoet.ClassName
import kotlin.reflect.KType

class Type(
    val packageName: String,
    val name: String
)

internal fun Type.toClassName() = ClassName(packageName, name)