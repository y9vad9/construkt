package com.construct.integration.coroutines

import com.construct.state.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class FlowState<T>(private val scope: CoroutineScope, private val flow: StateFlow<T>) : State<T> {
    override val value: T get() = flow.value

    override fun collect(collector: suspend (T) -> Unit) {
        scope.launch {
            flow.collect {
                collector(it)
            }
        }
    }
}