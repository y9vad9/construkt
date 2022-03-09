package com.construct.integration.androidx

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.construct.integration.coroutines.asState
import com.construct.state.State
import kotlinx.coroutines.flow.StateFlow

context(ComponentActivity)
fun <T> StateFlow<T>.asState(): State<T> {
    return asState(lifecycleScope)
}