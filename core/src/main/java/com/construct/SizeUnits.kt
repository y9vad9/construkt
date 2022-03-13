package com.construct

import android.util.DisplayMetrics

context(Context)
public val Int.dp: Int
    get() = resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT