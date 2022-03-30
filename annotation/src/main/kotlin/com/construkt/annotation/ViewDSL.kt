package com.construkt.annotation

/**
 * Code generation marker for views.
 * Using annotated type there will be generated DSL from it.
 * ## View
 * ### Example
 * ```kotlin
 * @ViewDSL
 * typealias TextView = android.widget.TextView
 * ```
 * ### Result
 * ```kotlin
 * interface TextViewDSLScope {
 *  fun text(text: String)
 *  fun textSize(textSize: Int)
 *  ...
 * }
 *
 * fun ViewGroup.textView(layoutParams: LayoutParams? = null, block: TextViewDSLScope.() -> Unit) {
 *  addView(TextViewDSLScopeImpl().apply(block))
 * }
 *
 * fun ViewGroup.textView(layoutParams: State<LayoutParams>, block: TextViewDSLScope.() -> Unit) {
 *  addView(TextViewDSLScopeImpl().apply(block))
 * }
 *
 * fun TextViewDSLScope.text(text: State<String>) {
 *  lifecycleOwner.lifecycleScope.launch {
 *      text.collect { text(it) }
 *  }
 * }
 *
 * fun TextViewDSLScope.textSize(textSize: State<Int>) {
 *  lifecycleOwner.lifecycleScope.launch {
 *      textSize.collect { textSize(it) }
 *  }
 * }
 * ```
 * ## LayoutParams
 * Also, it generates extensions for layout params of views that annotated.
 * ### Example
 * ```kotlin
 * public fun layoutParams(): ViewGroup.LayoutParams = ViewGroup.LayoutParams(-2, -2)
 *
 * public fun LinearLayout.LayoutParams.margins(
 *  p0: Int,
 *  p1: Int,
 *  p2: Int,
 *  p3: Int
 * ): LinearLayout.LayoutParams {
 *  setMargins(p0, p1, p2, p3)
 *  return this
 * }
 * ```
 * ## Builtins
 * Also, there is some builtins for types that used in views:
 * - [com.construkt.builtins.MenuDSLScope] for menus
 *
 * Consider contributing for builtins. We are always open for pull requests.
 */
@Target(AnnotationTarget.TYPEALIAS)
annotation class ViewDSL