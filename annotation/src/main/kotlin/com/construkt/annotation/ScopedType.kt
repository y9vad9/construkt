package com.construkt.annotation

import kotlin.reflect.KClass

/**
 * Code generation marker for types.
 *
 * It will take functions that returns specified type (in ScopedType annotation) and apply for it scope functions.
 * Code generation will take functions with type specified in [types] and make for it scope functions.
 * ### Definition
 * ```kotlin
 * @ScopedType(Editable::class)
 * typealias TextView = android.view.TextView
 * ```
 * ### Result
 * ```kotlin
 * textView {
 *  editableText {
 *      append("something")
 *  }
 * }
 * ```
 * @see [link](https://github.com/y9vad9/construkt/issues/7)
 */
@Target(AnnotationTarget.TYPEALIAS)
annotation class ScopedType(vararg val types: KClass<*>)