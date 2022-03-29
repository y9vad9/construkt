package com.y9vad9.construct.ksp

import com.construkt.codegen.CompoundCodeGenerator
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.y9vad9.construct.ksp.internal.isViewGroup
import com.y9vad9.construct.ksp.internal.resolveLayoutParams
import java.io.OutputStreamWriter

class ViewDSLVisitor(private val codeGenerator: CodeGenerator, private val logger: KSPLogger) :
    KSDefaultVisitor<Unit, KSClassDeclaration?>() {
    @OptIn(KotlinPoetKspPreview::class)
    override fun visitTypeAlias(typeAlias: KSTypeAlias, data: Unit): KSClassDeclaration {
        super.visitTypeAlias(typeAlias, data)
        val view = typeAlias.type.resolve().declaration as KSClassDeclaration
        val functions =
            view.getAllFunctions().filter { !it.simpleName.asString().contains("<") }.toList()
        val properties = view.getAllProperties().filter { it.isPublic() }.distinct()
        codeGenerator.createNewFile(
            Dependencies(false), view.packageName.asString(), view.simpleName.asString().plus("DSL")
        ).use { output ->
            OutputStreamWriter(output).use { writer ->
                CompoundCodeGenerator(
                    view.toClassName(),
                    isViewGroup(view),
                    resolveLayoutParams(functions),
                    functions.filter { it.isPublic() }.distinct(),
                    properties.toList()
                )
                    .generate()
                    .writeTo(writer)
            }
        }
        return view
    }

    override fun defaultHandler(node: KSNode, data: Unit): KSClassDeclaration? {
        return null
    }
}