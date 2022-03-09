package com.construct.codegen

import com.construct.descriptions.Func
import com.construct.descriptions.toClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

class InterfaceCodeGenerator(private val name: String, private val functions: List<Func>) : CodeGenerator<TypeSpec> {
    override fun generate(): TypeSpec {
        val funs = functions.filter { it.arguments.size > 1 }.map { func ->
            val arguments = func.arguments.map { ParameterSpec.builder(it.name, it.toClassName()).build() }
            FunSpec.builder(func.name)
                .addParameters(arguments)
                .returns(func.returnType.toClassName())
                .build()
        }
        val properties = functions.filter { it.arguments.size == 1 }.map { func ->
            val propertyName = func.name.removePrefix("set").replaceFirstChar { it.lowercase() }
            PropertySpec.builder(propertyName, func.arguments[0].toClassName())
                .mutable()
                .build()
        }
        return TypeSpec.interfaceBuilder(name)
            .addProperties(properties)
            .addFunctions(funs)
            .build()
    }
}