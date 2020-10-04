package com.test.newsappoffline.net

sealed class ApiResources<T>(val data: T? = null, val mesaage: String? = null) {

    class Success<T>(data: T, message: String) : ApiResources<T>(data, message)
    class Failed<T>(message: String) : ApiResources<T>(mesaage = message)
    class Loading<T>(message: String) : ApiResources<T>(mesaage = message)
}