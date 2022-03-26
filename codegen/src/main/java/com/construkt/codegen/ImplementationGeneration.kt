package com.construkt.codegen

import com.construkt.codegen.mapper.implementation.CollectedFunctionToImplMapper
import com.construkt.codegen.mapper.implementation.CollectedPropertyToFunImplMapper
import com.construkt.types.Android
import com.construkt.types.Annotations
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.*

class ImplementationGeneration(
    private val className: ClassName,
    private val interfaceName: ClassName,
    private val origin: ClassName,
    private val functions: List<KSFunctionDeclaration>,
    private val properties: List<KSPropertyDeclaration>,
    private val isViewGroup: Boolean
) : CodeGenerator<TypeSpec> {

    private fun TypeSpec.Builder.applyParameters(): TypeSpec.Builder {
        addProperties(
            listOf(
                PropertySpec.builder("context", Android.Context).initializer("context"),
                PropertySpec.builder("lifecycleOwner", Android.LifecycleOwner)
                    .initializer("lifecycleOwner")
            ).map { it.addModifiers(KModifier.OVERRIDE).build() }
        )
        addProperty(
            PropertySpec.builder("origin", origin).addModifiers(KModifier.OVERRIDE).initializer("origin").build()
        )
        primaryConstructor(
            FunSpec.constructorBuilder().addParameters(
                listOf(
                    ParameterSpec.builder("context", Android.Context).build(),
                    ParameterSpec.builder("origin", origin).build(),
                    ParameterSpec.builder("lifecycleOwner", Android.LifecycleOwner).build()
                )
            ).build()
        )
        return this
    }

    private fun TypeSpec.Builder.applyAddViewIfPossible(): TypeSpec.Builder = apply {
        if (isViewGroup) {
            addFunction(
                FunSpec.builder("addView")
                    .addAnnotation(Annotations.InternalConstruktApi)
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter("view", Android.View)
                    .addParameter("index", INT)
                    .addStatement("origin.addView(view, index)")
                    .build()
            )
            addFunction(
                FunSpec.builder("removeView")
                    .addAnnotation(Annotations.InternalConstruktApi)
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter("view", Android.View)
                    .addStatement("origin.removeView(view)")
                    .build()
            )
        }
    }

    override fun generate(): TypeSpec {
        return TypeSpec.classBuilder(className.simpleName)
            .addModifiers(KModifier.PRIVATE)
            .addSuperinterface(interfaceName)
            .addFunctions(functions.mapNotNull(CollectedFunctionToImplMapper))
            .addFunctions(properties.mapNotNull(CollectedPropertyToFunImplMapper))
            .applyParameters()
            .applyAddViewIfPossible()
            .build()
    }
}