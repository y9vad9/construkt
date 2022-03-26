package com.construkt.example.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.construkt.setContent

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppView()
        }
    }
}