package com.construkt

import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout

public fun layoutParams(): LayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
public fun marginLayoutParams(): MarginLayoutParams =
    MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
public fun linearLayoutParams(): LinearLayout.LayoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

public fun <T : LayoutParams> T.maxSize(): T = apply {
    width = LayoutParams.MATCH_PARENT
    height = LayoutParams.MATCH_PARENT
}

public fun <T : LayoutParams> T.size(size: Int): T = apply {
    width = size
    height = size
}

public fun <T : LayoutParams> T.wrapContentSize(): T = apply {
    width = LayoutParams.WRAP_CONTENT
    height = LayoutParams.WRAP_CONTENT
}

public fun <T : LayoutParams> T.wrapContentWidth(): T = apply {
    width = LayoutParams.WRAP_CONTENT
}

public fun <T : LayoutParams> T.wrapContentHeight(): T = apply {
    height = LayoutParams.WRAP_CONTENT
}

public fun <T : LayoutParams> T.wrapContent(): T = apply {
    height = LayoutParams.WRAP_CONTENT
    width = LayoutParams.WRAP_CONTENT
}

public fun <T : LayoutParams> T.maxWidth(): T = apply {
    width = LayoutParams.MATCH_PARENT
}

public fun <T : LayoutParams> T.maxHeight(): T = apply {
    height = LayoutParams.MATCH_PARENT
}