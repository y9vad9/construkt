package com.construkt.types

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName

object Builtins {
    val MenuDSL = ClassName("com.construkt.builtins", "MenuDSL")
    fun State(type: TypeName): ParameterizedTypeName =
        ClassName("com.construkt", "State").parameterizedBy(type)

    fun ViewGroupScope(type: TypeName) = ClassName(
        "com.construkt", "ViewGroupScope"
    ).parameterizedBy(type)

    fun ViewScope(type: TypeName) = ClassName(
        "com.construkt", "ViewScope"
    ).parameterizedBy(type)
}