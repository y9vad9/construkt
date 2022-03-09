package com.construct.codegen

import com.construct.descriptions.Func
import com.construct.descriptions.Type
import com.construct.descriptions.toClassName
import com.squareup.kotlinpoet.*

class ImplementationClassCodeGenerator(
    private val name: String,
    private val functions: List<Func>,
    private val origin: Type
) : CodeGenerator<TypeSpec> {
    override fun generate(): TypeSpec {
        val funs = functions.filter { it.arguments.size > 1 }.map { func ->
            val arguments = func.arguments.map { ParameterSpec.builder(it.name, it.toClassName()).build() }
            FunSpec.builder(func.name)
                .addParameters(arguments)
                .addCode(
                    """
                    return origin.${func.name}(${func.arguments.joinToString(", ") { it.name }}")
                """.trimIndent()
                )
                .returns(func.returnType.toClassName())
                .build()
        }
        val properties = functions.filter { it.arguments.size == 1 }.map { func ->
            val propertyName = func.name.removePrefix("set").replaceFirstChar { it.lowercase() }
            PropertySpec.builder(propertyName, func.arguments[0].toClassName())
                .mutable()
                .delegate("origin.$propertyName")
                .build()
        }
        return TypeSpec.interfaceBuilder(name)
            .primaryConstructor(
                FunSpec.constructorBuilder().addParameter(ParameterSpec.builder("origin", origin.toClassName()).build())
                    .build()
            )
            .addProperty(PropertySpec.builder("origin", origin.toClassName()).initializer("origin").build())
            .addProperties(properties)
            .addFunctions(funs)
            .build()
    }
}