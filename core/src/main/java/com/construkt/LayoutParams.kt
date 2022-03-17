package com.construkt

import android.view.ViewGroup.LayoutParams

public fun layoutParams(): LayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

public fun LayoutParams.maxSize(): LayoutParams = apply {
    width = LayoutParams.MATCH_PARENT
    height = LayoutParams.MATCH_PARENT
}

public fun LayoutParams.wrapContentSize(): LayoutParams = apply {
    width = LayoutParams.WRAP_CONTENT
    height = LayoutParams.WRAP_CONTENT
}

public fun LayoutParams.wrapContentWidth(): LayoutParams = apply {
    width = LayoutParams.WRAP_CONTENT
}

public fun LayoutParams.wrapContentHeight(): LayoutParams = apply {
    width = LayoutParams.WRAP_CONTENT
    height = LayoutParams.WRAP_CONTENT
}

public fun LayoutParams.maxWidth(): LayoutParams = apply {
    width = LayoutParams.MATCH_PARENT
}

public fun LayoutParams.maxHeight(): LayoutParams = apply {
    height = LayoutParams.MATCH_PARENT
}