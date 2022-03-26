package com.construkt.annotation


@InternalConstruktApi
@RequiresOptIn(message = "Always throws UnsupportedOperationException", level = RequiresOptIn.Level.ERROR)
public annotation class Unsupported