package com.construkt

import android.content.res.ColorStateList

public fun ColorStateList(vararg colors: Pair<Int, Int>, default: Int? = null): ColorStateList {
    return ColorStateList(colors.map { intArrayOf(it.first) }.toTypedArray().let {
        if (default != null) it.plus(intArrayOf()) else it
    }, colors.map { it.second }.toIntArray().let { if (default != null) it.plus(default) else it })
}