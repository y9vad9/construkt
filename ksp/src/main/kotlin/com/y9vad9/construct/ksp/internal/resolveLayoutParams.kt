package com.y9vad9.construct.ksp.internal

import com.construkt.models.LayoutParamsModel
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName

@OptIn(KotlinPoetKspPreview::class)
internal fun resolveLayoutParams(functions: List<KSFunctionDeclaration>): LayoutParamsModel {
    val type = functions.firstOrNull {
        it.simpleName.asString() == "generateDefaultLayoutParams"
    }?.returnType?.resolve()?.declaration
        as? KSClassDeclaration ?: return LayoutParamsModel(
        ClassName(
            "android.view",
            "ViewGroup.LayoutParams"
        ), emptyList(), emptyList()
    )
    return LayoutParamsModel(
        type.toClassName(),
        type.getAllFunctions()
            .filterLayoutParamsFunctions()
            .toList(),
        type.getAllProperties().filter { it.isMutable }.toList(),
    )
}