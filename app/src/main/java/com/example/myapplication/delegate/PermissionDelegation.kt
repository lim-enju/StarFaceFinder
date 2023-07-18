package com.example.myapplication.delegate

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.starFaceFinder.data.common.TAG

class PermissionDelegation : IPermissionDelegation {

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var multiplePermissionLauncher: ActivityResultLauncher<Array<String>>


    override fun checkPermission(context: Context, permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED

    override fun checkOrRequestPermission(activity: FragmentActivity, vararg permissions: String) {
        when {
            //요청이 한개만 들어온 경우
            permissions.size == 1 -> {
                if(!checkPermission(activity, permissions[0])){
                    permissionLauncher = activity.registerForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { isGranted ->
                        Log.d(TAG, "setupActivityResultLauncher: $isGranted")
                    }
                    permissionLauncher.launch(permissions[0])
                }
            }
            //요청이 한개 이상 들어온 경우
            permissions.size > 1 -> {
                permissions.forEach { permission ->
                    if(!checkPermission(activity, permission)){
                        multiplePermissionLauncher = activity.registerForActivityResult(
                            ActivityResultContracts.RequestMultiplePermissions()
                        ) { isGranted ->
                            Log.d(TAG, "setupActivityResultLauncher: $isGranted")
                        }
//                            multiplePermissionLauncher.launch(permission.toTypeArray())
                    }
                }
            }
            else -> {
                //요청받은 권한이 없는 경우
            }
        }
    }
}

