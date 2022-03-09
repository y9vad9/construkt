package com.construct.state

public interface State<T> {
    /**
     * Current value in [State].
     */
    public val value: T

    /**
     * Collects values that arrive to [State].
     */
    public fun collect(collector: suspend () -> Unit)
}

public fun <T> emptyState(value: T): State<T> = object : State<T> {
    override val value: T = value
    override fun collect(collector: suspend () -> Unit) = Unit
}