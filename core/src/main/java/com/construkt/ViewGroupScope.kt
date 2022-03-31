package com.construkt

import android.view.View
import android.view.ViewGroup
import com.construkt.annotation.InternalConstruktApi

/**
 * Basic scope of any [ViewGroup] views.
 */
public interface ViewGroupScope<T : ViewGroup> : ViewScope<T> {
    @InternalConstruktApi
    public fun addView(view: View, index: Int = -1)

    @InternalConstruktApi
    public fun removeView(view: View)
}