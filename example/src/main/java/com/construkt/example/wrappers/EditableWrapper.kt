package com.construkt.example.wrappers

import android.text.Editable

class EditableWrapper(private val editable: Editable) {
    operator fun plus(another: String) {
        editable.append(another)
    }
}