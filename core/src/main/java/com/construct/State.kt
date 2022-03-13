package com.construct

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public typealias State<T> = StateFlow<T>
public typealias MutableState<T> = MutableStateFlow<T>