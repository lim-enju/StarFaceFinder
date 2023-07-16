package com.example.myapplication.utils

import android.util.TypedValue
import com.example.myapplication.MainApplication

fun Int.dpToPx (): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
    MainApplication.appContext.resources.displayMetrics).toInt()

fun Float.dpToPx (): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this,
    MainApplication.appContext.resources.displayMetrics).toInt()