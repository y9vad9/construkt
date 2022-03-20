package com.construkt

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.construkt.annotation.ConstruktDSL

/**
 * Basic scope of every DSL view builders.
 */
@ConstruktDSL
public interface ViewScope {
    /**
     * Current context.
     */
    public val context: Context

    /**
     * Current lifecycle owner
     */
    public val lifecycleOwner: LifecycleOwner
}