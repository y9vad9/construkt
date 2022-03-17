package com.construkt.example.app

import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.construkt.layoutParams
import com.construkt.maxSize
import com.construkt.setContent
import com.construkt.wrapContentSize
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AppActivity : AppCompatActivity() {
    private val resId = MutableStateFlow(android.R.drawable.btn_star)
    private val text = MutableStateFlow("Hello, World")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            linearLayout(layoutParams().maxSize()) {
                orientation(LinearLayout.VERTICAL)

                imageView(layoutParams().wrapContentSize()) {
                    imageResource(resId)
                    gravity(Gravity.CENTER)
                }

                textView(layoutParams().wrapContentSize()) {
                    text(text)
                }
            }
        }


        lifecycleScope.launch {
            delay(3000L)
            text.value = "Bye, world!"
            resId.value = android.R.drawable.btn_plus
        }
    }
}