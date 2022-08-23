package com.example.clothesstore.utils

sealed class Resource<T>(data: T? = null, errorMessage: String? = null, isLoading: Boolean = false) {
    class Success<T>(data: T? = null): Resource<T>(data = data)
    class Error<T>(errorMessage: String? = null): Resource<T>(errorMessage = errorMessage)
    class Loading<T>(isLoading: Boolean): Resource<T>(isLoading = isLoading)
}
