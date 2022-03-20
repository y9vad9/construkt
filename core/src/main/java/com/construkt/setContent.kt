package com.construkt

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleOwner
import com.construkt.annotation.InternalConstruktApi

private class RootViewScope(override val context: Context, override val lifecycleOwner: LifecycleOwner) : ViewGroupScope {
    val rootView = FrameLayout(context)

    @InternalConstruktApi
    override fun removeView(view: View) {
        rootView.removeView(view)
    }

    @InternalConstruktApi
    override fun addView(view: View) {
        rootView.addView(view)
    }
}

public fun ComponentActivity.setContent(builder: ViewGroupScope.() -> Unit) {
    setContentView(RootViewScope(this, this).apply(builder).rootView)
}