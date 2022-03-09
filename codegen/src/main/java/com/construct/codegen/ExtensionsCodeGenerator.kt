package com.construct.codegen

import com.construct.descriptions.Func
import com.construct.descriptions.Type
import com.construct.descriptions.toClassName
import com.construct.state.State
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

class ExtensionsCodeGenerator(
    private val name: String,
    private val packageName: String,
    private val functions: List<Func>
) : CodeGenerator<Pair<List<PropertySpec>, List<FunSpec>>> {
    override fun generate(): Pair<List<PropertySpec>, List<FunSpec>> {
        val funs = functions.filter { it.arguments.size > 1 }.map { func ->
            val arguments = func.arguments.map {
                ParameterSpec.builder(
                    it.name,
                    State::class.asClassName().parameterizedBy(it.toClassName())
                ).build()
            }
            FunSpec.builder(func.name)
                .addParameters(arguments)
                .addCode(arguments.joinToString("\n") { "${it.name}.collect { ${it.name} = it }" })
                .returns(func.returnType.toClassName())
                .build()
        }
        val properties = functions.filter { it.arguments.size == 1 }.map { func ->
            val propertyName = func.name.removePrefix("set").replaceFirstChar { it.lowercase() }
            val propertyType = State::class.asClassName().parameterizedBy(func.arguments[0].toClassName())
            PropertySpec.builder(propertyName, propertyType)
                .receiver(ClassName(packageName, name))
                .mutable()
                .setter(
                    FunSpec.getterBuilder()
                        .addParameter("value", propertyType)
                        .addCode(
                            """
                            value.collect { $propertyName = it }
                            field = value
                        """.trimIndent()
                        )
                        .build()
                )
                .build()
        }
        return properties to funs
    }
}