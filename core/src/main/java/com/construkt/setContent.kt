package com.construkt

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleOwner
import com.construkt.annotation.InternalConstruktApi

internal class RootViewScope(override val context: Context, override val lifecycleOwner: LifecycleOwner) :
    ViewGroupScope<LinearLayout> {
    @InternalConstruktApi
    override fun addView(view: View, index: Int) {
        origin.addView(view, index)
    }

    @InternalConstruktApi
    override fun removeView(view: View) {
        origin.removeView(view)
    }

    @InternalConstruktApi
    override val origin = LinearLayout(context).apply {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        orientation = LinearLayout.VERTICAL
    }
}

@OptIn(InternalConstruktApi::class)
public fun ComponentActivity.setContent(builder: ViewGroupScope<LinearLayout>.() -> Unit) {
    setContentView(RootViewScope(this, this).apply(builder).origin)
}