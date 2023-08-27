package com.example.myapplication.utils

data class DeepLinkData(
    val actionType: ActionType?,
    val parameters: Map<String, String>?
)

enum class ActionType {
    VIEW_HISTORY
}