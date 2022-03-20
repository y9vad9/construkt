package com.construkt

import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams

public fun layoutParams(): LayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
public fun marginLayoutParams(): MarginLayoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

public fun <T : LayoutParams> T.maxSize(): T = apply {
    width = LayoutParams.MATCH_PARENT
    height = LayoutParams.MATCH_PARENT
}

public fun <T : LayoutParams> T.height(size: Int): T = apply {
    height = size
}

public fun <T : LayoutParams> T.width(size: Int): T = apply {
    width = size
}

public fun <T : LayoutParams> T.wrapContentSize(): T = apply {
    width = LayoutParams.WRAP_CONTENT
    height = LayoutParams.WRAP_CONTENT
}

public fun <T : LayoutParams> T.wrapContentWidth(): T = apply {
    width = LayoutParams.WRAP_CONTENT
}

public fun <T : LayoutParams> T.wrapContentHeight(): T = apply {
    width = LayoutParams.WRAP_CONTENT
    height = LayoutParams.WRAP_CONTENT
}

public fun <T : LayoutParams> T.maxWidth(): T = apply {
    width = LayoutParams.MATCH_PARENT
}

public fun <T : LayoutParams> T.maxHeight(): T = apply {
    height = LayoutParams.MATCH_PARENT
}

public fun MarginLayoutParams.margin(size: Int): MarginLayoutParams = apply {
    setMargins(size, size, size, size)
}

public fun MarginLayoutParams.marginStart(size: Int): MarginLayoutParams = apply {
    marginStart = size
}

public fun MarginLayoutParams.marginEnd(size: Int): MarginLayoutParams = apply {
    marginEnd = size
}

public fun MarginLayoutParams.marginBottom(size: Int): MarginLayoutParams = apply {
    bottomMargin = size
}

public fun MarginLayoutParams.marginTop(size: Int): MarginLayoutParams = apply {
    topMargin = size
}