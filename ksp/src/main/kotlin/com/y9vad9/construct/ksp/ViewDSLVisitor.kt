package com.y9vad9.construct.ksp

import com.construct.codegen.CompoundCodeGenerator
import com.construct.descriptions.Func
import com.construct.descriptions.Property
import com.construct.descriptions.Type
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.google.devtools.ksp.symbol.KSVisitorVoid
import java.io.OutputStreamWriter

class ViewDSLVisitor(private val codeGenerator: CodeGenerator) : KSVisitorVoid() {
    override fun visitTypeAlias(typeAlias: KSTypeAlias, data: Unit) {
        super.visitTypeAlias(typeAlias, data)
        val view = typeAlias.type.resolve() as KSClassDeclaration
        val packageName = view.packageName.asString()
        codeGenerator.createNewFile(
            Dependencies(true), view.packageName.asString(), view.simpleName.asString().plus("DSL")
        ).use { output ->
            OutputStreamWriter(output).use { writer ->
                CompoundCodeGenerator(
                    Type(packageName, view.simpleName.asString()),
                    view.getAllFunctions().map { function ->
                        val returnType = function.returnType?.resolve()
                        Func(
                            function.simpleName.asString(),
                            function.parameters.map {
                                val type = it.type.resolve()
                                Type(type.declaration.packageName.asString(), type.declaration.simpleName.asString())
                            },
                            Type(
                                returnType?.declaration?.packageName?.asString() ?: "kotlin",
                                returnType?.declaration?.simpleName?.asString() ?: "Unit"
                            )
                        )
                    }.toList(),
                    view.getAllProperties().map { property ->
                        Property(
                            property.simpleName.asString(),
                            property.type.resolve().declaration.let {
                                Type(
                                    it.packageName.asString(),
                                    it.simpleName.asString()
                                )
                            })
                    }.toList()
                )
            }
        }

    }
}