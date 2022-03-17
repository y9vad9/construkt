package com.construkt

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleOwner
import com.construkt.annotation.InternalConstructApi

private class RootViewScope(override val context: Context, override val lifecycleOwner: LifecycleOwner) : ViewScope {
    val rootView = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
    }

    @InternalConstructApi
    override fun addView(view: View) {
        rootView.addView(view)
    }
}

public fun ComponentActivity.setContent(builder: ViewScope.() -> Unit) {
    setContentView(RootViewScope(this, this).apply(builder).rootView)
}