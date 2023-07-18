package com.example.myapplication.delegate

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * @see DefaultLifecycleObserver onCreate에서 launcher를 초기화 해주기 위해
 * DefaultLifecycleObserver 를 상속받음
 */
class PermissionDelegation(
    private val registry: ActivityResultRegistry
) : IPermissionDelegation, DefaultLifecycleObserver {

    private var requestMultiplePermissionContinuation: CancellableContinuation<Map<String, Boolean>>? = null
    private var requestPermissionContinuation: CancellableContinuation<Boolean>? = null

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var multiplePermissionLauncher: ActivityResultLauncher<Array<String>>

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(owner: LifecycleOwner) {
        permissionLauncher = registry.register(
            "key",
            owner,
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            requestPermissionContinuation?.resume(isGranted, null)
        }

        multiplePermissionLauncher = registry.register(
            "key",
            owner,
            ActivityResultContracts.RequestMultiplePermissions()
        ) { grantedMap ->
            requestMultiplePermissionContinuation?.resume(grantedMap, null)
        }
    }

    override fun checkPermission(context: Context, permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED

    override suspend fun checkOrRequestPermission(
        activity: FragmentActivity,
        permissions: Array<String>,
    ): Map<String, Boolean> = suspendCancellableCoroutine { continuation ->
        requestMultiplePermissionContinuation = continuation

        //허용 되지 않은 권한이 있는지 확인
        val isNotGrantedPermission =
            permissions.any { permission -> !checkPermission(activity, permission) }

        if(isNotGrantedPermission){
            //권한 요청
            multiplePermissionLauncher.launch(permissions)
        }

        continuation.invokeOnCancellation {
            requestMultiplePermissionContinuation = null
        }
    }

    override suspend fun checkOrRequestPermission(
        activity: FragmentActivity,
        permissions: String
    ): Boolean = suspendCancellableCoroutine { continuation ->
        requestPermissionContinuation = continuation

        //권한 허용 여부 확인
        if (!checkPermission(activity, permissions)) {
            //권한 요청
            permissionLauncher.launch(permissions)
        }

        continuation.invokeOnCancellation {
            requestPermissionContinuation = null
        }
    }
}

