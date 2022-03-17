package com.construkt

import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.construkt.annotation.InternalConstructApi

/**
 * Basic scope of every DSL view builders.
 */
public interface ViewScope {
    /**
     * Current context.
     */
    public val context: Context

    /**
     * Current lifecycle owner
     */
    public val lifecycleOwner: LifecycleOwner

    /**
     * Adds view into views hierarchy.
     * Available even for not [android.view.ViewGroup]s to disallow invalid dsl scope adding.
     * @throws [IllegalStateException]
     */
    @InternalConstructApi
    @Throws(IllegalStateException::class)
    public fun addView(view: View) {
        throw IllegalStateException("Unsupported")
    }
}