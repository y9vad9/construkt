package com.construkt

import android.view.View
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
context(ViewGroupScope)
public fun <T> State<T>.constructOnEach(block: ViewGroupScope.(T) -> Unit) {
    var previous = value
    val stated = StatedViewGroup()
    block(stated, value)
    lifecycleOwner.lifecycleScope.launch {
        collect {
            if(previous != it) {
                stated.removeAll()
                block(stated, it)
                previous = it
            }
        }
    }
}

private fun ViewGroupScope.StatedViewGroup(): StatedViewGroup = StatedViewGroup(this)

private class StatedViewGroup(val scope: ViewGroupScope) : ViewGroupScope by scope {
    private val tree: MutableList<View> = mutableListOf()
    @OptIn(InternalConstruktApi::class)
    fun removeAll() {
        tree.forEach { scope.removeView(it) }
    }

    @OptIn(InternalConstruktApi::class)
    override fun addView(view: View) {
        tree.add(view)
        scope.addView(view)
    }
}