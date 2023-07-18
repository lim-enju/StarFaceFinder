package com.example.myapplication.delegate

import android.content.Context
import androidx.fragment.app.FragmentActivity

interface IPermissionDelegation {

    /**
     * 권한이 허용되었거나 거부되었는지를 확인하는 함수 입니다.
     *
     * @param permission 확인하려고 하는 권한
     */
    fun checkPermission(context: Context, permission: String): Boolean

    /**
     * 권한 동의 여부를 확인하고 동의되어 있지 않은 경우 권한을 요청하는 함수입니다.
     *
     * @param permissions 확인하려고 하는 권한
     * @return 권한 요청 결과
     * key string은 권한를 의미하고 value Boolean은 요청 결과를 의미합니다.
     */
    suspend fun checkOrRequestPermission(
        activity: FragmentActivity,
        permissions: Array<String>,
    ): Map<String, Boolean>

    /**
     * 권한 동의 여부를 확인하고 동의되어 있지 않은 경우 권한을 요청하는 함수입니다.
     *
     * @param permissions 확인하려고 하는 권한
     * @return 권한 요청 결과
     * key string은 권한를 의미하고 value Boolean은 요청 결과를 의미합니다.
     */
    suspend fun checkOrRequestPermission(
        activity: FragmentActivity,
        permissions: String,
    ): Boolean
}