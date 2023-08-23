package com.starFaceFinder.data.model

class ErrorException(override val message: String, throwable: Throwable) : Exception(message, throwable) { }