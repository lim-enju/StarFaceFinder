package com.example.myapplication

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.utils.ActionType
import com.example.myapplication.utils.DeepLinkData
import com.starFaceFinder.data.common.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private fun extractDeepLinkData(data: Uri): DeepLinkData? {
        //지정되지 않은 action인 경우 null 반환
        val actionString = data.getQueryParameter("action")
        val actionType = actionString?.let { ActionType.valueOf(it) }?: return null

        //딥링크 파라미터 맵핑
        val params = mutableMapOf<String, String>()
        for(paramName in data.queryParameterNames){
            val value = data.getQueryParameter(paramName)
            if(paramName != "action" && value != null) {
                params[paramName] = value
            }
        }

        return DeepLinkData(actionType, params)
    }

    fun handleDeepLinkData(data: Uri){
        val deepLinkData = extractDeepLinkData(data)?: return
        when(deepLinkData.actionType){
            ActionType.VIEW_HISTORY -> {
                //링크 처리
            }

            else -> {}
        }

        Log.d(TAG, "handleDeepLinkData: $deepLinkData")
    }
}