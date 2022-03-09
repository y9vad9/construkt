package com.construct.annotation

import android.view.ViewGroup.LayoutParams
import kotlin.reflect.KClass

@Target(AnnotationTarget.TYPEALIAS)
public annotation class ViewDSL(val layoutParamsClass: KClass<LayoutParams>)
