package com.example.myapplication.delegate

import android.content.Context
import androidx.fragment.app.FragmentActivity

interface IPermissionDelegation {

    fun checkPermission(context: Context, permission: String): Boolean

    fun checkOrRequestPermission(activity: FragmentActivity, vararg permissions: String)
}