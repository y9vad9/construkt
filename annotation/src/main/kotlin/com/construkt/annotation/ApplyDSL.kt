package com.construkt.annotation

import kotlin.reflect.KClass

/**
 * Applies DSL Scope function to [forType] from [using] class.
 * [using] class should have one parameter constructor with type [T].
 *
 * ### Definition
 * ```kotlin
 * @ApplyDSL(forType = Editable::class, using = EditableDSLScope::class)
 * @ViewDSL
 * typealias TextView = android.view.TextView
 *
 * public class EditableDSLScope(private val editable: Editable) {
 *  operator fun plus(other: String) = editable.append(other)
 * }
 * ```
 * ### Result
 * ```kotlin
 * textView {
 * // get / set prefixes will be removed
 *  editableText {
 *      +"something"
 *  }
 * }
 * ```
 * @see [link](https://github.com/y9vad9/construkt/issues/6).
 */
@Repeatable
@Target(AnnotationTarget.TYPEALIAS)
annotation class ApplyDSL(val forType: KClass<*>, val using: KClass<*>)