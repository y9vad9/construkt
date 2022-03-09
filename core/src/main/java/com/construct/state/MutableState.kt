package com.construct.state

public interface MutableState<T> : State<T> {
    override var value: T
}