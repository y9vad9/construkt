package com.construkt.annotation

/**
 * Marks that API shouldn't be used outside DSL generation.
 */
@RequiresOptIn(
    message = "This API shouldn't be used outside DSL generation. Internal API.",
    level = RequiresOptIn.Level.WARNING
)
public annotation class InternalConstructApi