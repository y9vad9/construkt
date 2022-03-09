package com.construct

import android.content.Context
import android.view.View
import com.construct.annotation.InternalConstructApi

/**
 * Basic scope of every DSL view builders.
 */
public interface ViewScope {
    /**
     * Current context.
     */
    public val context: Context
    /**
     * Adds view into views hierarchy.
     * Available even for not [android.view.ViewGroup]s to disallow invalid dsl scope adding.
     * @throws [IllegalStateException]
     */
    @InternalConstructApi
    @Throws(IllegalStateException::class)
    public fun addView(view: View)
}