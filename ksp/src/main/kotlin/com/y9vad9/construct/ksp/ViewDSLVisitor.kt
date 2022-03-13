package com.y9vad9.construct.ksp

import com.construct.codegen.CompoundCodeGenerator
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.y9vad9.construct.ksp.internal.filterFunctions
import com.y9vad9.construct.ksp.internal.isViewGroup
import com.y9vad9.construct.ksp.internal.resolveLayoutParams
import java.io.OutputStreamWriter

class ViewDSLVisitor(private val codeGenerator: CodeGenerator) : KSVisitorVoid() {

    @OptIn(KotlinPoetKspPreview::class)
    override fun visitTypeAlias(typeAlias: KSTypeAlias, data: Unit) {
        super.visitTypeAlias(typeAlias, data)
        val view = typeAlias.type.resolve().declaration as KSClassDeclaration
        val functions = view.getAllFunctions().filterFunctions().distinct()
        val properties = view.getAllProperties().filter { !it.isPublic() }.distinct()
        codeGenerator.createNewFile(
            Dependencies(false), view.packageName.asString(), view.simpleName.asString().plus("DSL")
        ).use { output ->
            OutputStreamWriter(output).use { writer ->
                CompoundCodeGenerator(
                    view.toClassName(),
                    isViewGroup(view),
                    resolveLayoutParams(view).toClassName(),
                    functions.toList(),
                    properties.toList()
                )
                    .generate()
                    .writeTo(writer)
            }
        }

    }
}