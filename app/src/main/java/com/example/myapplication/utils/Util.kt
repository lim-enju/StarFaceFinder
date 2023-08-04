package com.example.myapplication.utils

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.TypedValue
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.MainApplication

//TODO:: 파일을 나눠서 가지고 있어야 하나..

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

val AndroidViewModel.context: Context
    get() = getApplication<Application>().applicationContext


fun Int.dpToPx (): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
    MainApplication.appContext.resources.displayMetrics).toInt()

fun Float.dpToPx (): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this,
    MainApplication.appContext.resources.displayMetrics).toInt()
