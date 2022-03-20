package com.construkt

import android.util.DisplayMetrics

context(ViewScope)
public val Int.dp: Int
    get() = this * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)