package com.y9vad9.construct.ksp

import com.construkt.codegen.CompoundCodeGenerator
import com.construkt.models.ResolvedViewModel
import com.construkt.types.Annotations
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.y9vad9.construct.ksp.internal.isViewGroup
import com.y9vad9.construct.ksp.internal.resolveLayoutParams
import java.io.OutputStreamWriter
import kotlin.reflect.KClass

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
                CompoundCodeGenerator(ResolvedViewModel(view.toClassName(),
                    typeAlias.annotations.filter { it.annotationType.resolve().toClassName() == Annotations.ApplyDSL }.map {
                            ResolvedViewModel.WrappedType(
                                (it.arguments.first().value as KSType).toClassName(),
                                (it.arguments[1].value as KSType).toClassName()
                            )
                        }.toList(),
                    typeAlias.annotations.filter {
                        it.annotationType.resolve().toClassName() == Annotations.ScopedType
                    }
                        .map { annotation -> (annotation.arguments.first().value as ArrayList<KSType>)
                            .map { it.toClassName() } }
                        .flatten().map { ResolvedViewModel.ScopedType(it) }.toList()),
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