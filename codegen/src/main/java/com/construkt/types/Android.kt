package com.construkt.types

import com.squareup.kotlinpoet.ClassName

object Android {
    val Menu = ClassName("android.view", "Menu")
    val View = ClassName("android.view", "View")
    val ViewGroup = ClassName("android.view", "ViewGroup")
    val Context = ClassName("android.content", "Context")
    val LifecycleOwner = ClassName("androidx.lifecycle", "LifecycleOwner")
}