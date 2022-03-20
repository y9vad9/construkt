package com.construkt

import android.content.Context
import android.util.DisplayMetrics

context(Context)
public val Int.dp: Int
    get() = this * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)