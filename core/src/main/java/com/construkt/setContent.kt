package com.construkt

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleOwner
import com.construkt.annotation.InternalConstruktApi

internal class RootViewScope(override val context: Context, override val lifecycleOwner: LifecycleOwner) :
    ViewGroupScope<FrameLayout> {
    @InternalConstruktApi
    override fun addView(view: View, index: Int) {
        origin.addView(view, index)
    }

    @InternalConstruktApi
    override fun removeView(view: View) {
        origin.removeView(view)
    }

    @InternalConstruktApi
    override val origin = FrameLayout(context)
}

@OptIn(InternalConstruktApi::class)
public fun ComponentActivity.setContent(builder: ViewGroupScope<FrameLayout>.() -> Unit) {
    setContentView(RootViewScope(this, this).apply(builder).origin)
}