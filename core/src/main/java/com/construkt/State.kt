package com.construkt

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.construkt.annotation.InternalConstruktApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

public typealias State<T> = StateFlow<T>
public typealias MutableState<T> = MutableStateFlow<T>

/**
 * Constructs view hierarchy on each state changing.
 * If state same to previous then it won't reconstruct.
 * If state is different, then it will remove everything that constructed inside [block] and recall it.
 */
context(ViewGroupScope<V>)
    public fun <T, V : ViewGroup> State<T>.constructOnEach(block: ViewGroupScope<V>.(T) -> Unit) {
    var previous = value

    @OptIn(InternalConstruktApi::class)
    val stated = statedViewGroup(origin.childCount)
    block(stated, value)
    lifecycleOwner.lifecycleScope.launch {
        collect {
            if (previous != it) {
                stated.removeAll()
                block(stated, it)
                previous = it
            }
        }
    }
}

private fun <T : ViewGroup> ViewGroupScope<T>.statedViewGroup(last: Int): StatedViewGroup<T> =
    StatedViewGroup(this, last)

private class StatedViewGroup<T : ViewGroup>(val scope: ViewGroupScope<T>, private val last: Int) :
    ViewGroupScope<T> by scope {
    private val tree: MutableList<View> = mutableListOf()
    private var latest = last

    @OptIn(InternalConstruktApi::class)
    fun removeAll() {
        tree.forEach { scope.origin.removeView(it) }
        latest = last
    }

    @OptIn(InternalConstruktApi::class)
    override fun addView(view: View, index: Int) {
        latest++
        origin.addView(view, latest)
    }
}