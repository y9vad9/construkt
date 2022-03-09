package com.construct.integration.coroutines

import com.construct.state.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

fun <T> StateFlow<T>.asState(scope: CoroutineScope): State<T> = FlowState(scope, this)