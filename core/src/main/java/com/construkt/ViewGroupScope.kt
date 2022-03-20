package com.construkt

import android.view.View
import com.construkt.annotation.InternalConstruktApi

public interface ViewGroupScope : ViewScope {
    @InternalConstruktApi
    public fun removeView(view: View)
    /**
     * Adds view into views hierarchy.
     */
    @InternalConstruktApi
    public fun addView(view: View)
}