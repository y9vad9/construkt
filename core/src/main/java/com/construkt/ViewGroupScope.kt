package com.construkt

import android.view.View
import android.view.ViewGroup
import com.construkt.annotation.InternalConstruktApi

public interface ViewGroupScope<T : ViewGroup> : ViewScope<T> {
    @InternalConstruktApi
    public fun addView(view: View, index: Int = 0)

    @InternalConstruktApi
    public fun removeView(view: View)
}