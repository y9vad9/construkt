package com.construkt

import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View

context(ViewScope<T>)
public val <T : View> Int.dp: Int
    get() = this * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

context(ViewScope<T>)
    public val <T : View> Float.sp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, context.resources.displayMetrics)

context(ViewScope<T>)
    public val <T : View> Int.sp: Int get() = toFloat().sp.toInt()

context(ViewScope<T>)
    public val <T : View> Float.dp: Float
    get() = this * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)