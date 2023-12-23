package com.example.myapplication.utils

import android.graphics.Outline
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.EditText
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

@BindingAdapter("app:radius")
fun View.setRadius(radius: Float) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, view.width, view.height, radius)
        }
    }
    clipToOutline = true
}

@BindingAdapter(value = ["app:strokeWidth", "app:strokeColor"], requireAll = true)
fun View.setStrokeWidth(width: Float, color: Int){
    val gradientBackground = GradientDrawable()
    gradientBackground.setStroke(width.toInt(), color)
    background = gradientBackground
}

fun EditText.textChangesToFlow(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(text)
            }
        }
        addTextChangedListener(listener)
        //콜백이 사라질 때 리스너 제거
        awaitClose {
            removeTextChangedListener(listener)
        }
    }.onStart {
        emit(text)
    }
}