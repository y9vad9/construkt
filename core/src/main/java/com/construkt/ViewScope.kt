package com.construkt

import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.construkt.annotation.ConstruktDSL
import com.construkt.annotation.InternalConstruktApi

/**
 * Basic scope of every DSL view builders.
 */
@ConstruktDSL
public interface ViewScope<T : View> {
    /**
     * Current context.
     */
    public val context: Context

    @InternalConstruktApi
    public val origin: T

    /**
     * Current lifecycle owner
     */
    public val lifecycleOwner: LifecycleOwner
}