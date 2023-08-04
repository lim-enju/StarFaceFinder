package com.example.myapplication.utils

import android.graphics.Outline
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider
import androidx.databinding.BindingAdapter
import com.example.myapplication.MainApplication

fun Int.dpToPx (): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
    MainApplication.appContext.resources.displayMetrics).toInt()

fun Float.dpToPx (): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this,
    MainApplication.appContext.resources.displayMetrics).toInt()

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