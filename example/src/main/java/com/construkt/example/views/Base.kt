package com.construkt.example.views

import android.text.Editable
import com.construkt.annotation.ApplyDSL
import com.construkt.annotation.ScopedType
import com.construkt.annotation.ViewDSL
import com.construkt.example.wrappers.EditableWrapper
import com.google.android.material.bottomnavigation.BottomNavigationView

@ScopedType(Editable::class)
@ViewDSL
typealias TextView = android.widget.TextView

@ViewDSL
typealias LinearLayout = android.widget.LinearLayout

@ViewDSL
typealias FrameLayout = android.widget.FrameLayout

@ViewDSL
typealias ImageView = android.widget.ImageView

@ApplyDSL(forType = Editable::class, EditableWrapper::class)
@ViewDSL
typealias Button = android.widget.Button

@ViewDSL
typealias BottomNavigationView = BottomNavigationView