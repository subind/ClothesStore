package com.example.clothesstore.utils

sealed class Resource<T>(val data: T? = null, val errorMessage: String? = null, val isLoading: Boolean = false) {
    class Success<T>(data: T? = null): Resource<T>(data = data)
    class Error<T>(errorMessage: String? = null): Resource<T>(errorMessage = errorMessage)
    class Loading<T>(isLoading: Boolean = false): Resource<T>(isLoading = isLoading)
}
