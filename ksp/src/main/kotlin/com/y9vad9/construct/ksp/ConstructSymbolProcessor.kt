package com.y9vad9.construct.ksp

import com.construkt.annotation.ViewDSL
import com.construkt.codegen.LayoutParamsExtensionsGenerator
import com.construkt.codegen.LayoutParamsFileGenerator
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.y9vad9.construct.ksp.internal.resolveLayoutParams
import java.io.OutputStreamWriter

class ConstructSymbolProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {
    private var evaluated = false

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (evaluated)
            return listOf()
        val annotated =
            resolver.getAllFiles().flatMap { it.declarations }.filter { it.isAnnotationPresent(ViewDSL::class) }

        evaluated = true

        val resolved = annotated.toList().mapNotNull {
            it.accept(ViewDSLVisitor(environment.codeGenerator, environment.logger), Unit)
        }

        val layoutParamsFunctions = resolved.asSequence()
            .map { resolveLayoutParams(it.getAllFunctions().toList()) }
            .distinctBy { it.name.packageName + "." + it.name.simpleNames.joinToString(".") }
            .map { LayoutParamsExtensionsGenerator(it.name, it.functions, it.properties).generate() }
            .flatten()

        environment.codeGenerator.createNewFile(
            Dependencies(false),
            "com.construkt.generated.layoutParams",
            "extensions"
        ).use {
            OutputStreamWriter(it).use { writer ->
                LayoutParamsFileGenerator(layoutParamsFunctions.toList()).generate().writeTo(writer)
            }
        }

        return listOf()
    }
}