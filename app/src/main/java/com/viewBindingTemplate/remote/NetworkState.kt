package com.viewBindingTemplate.remote

sealed class NetworkState<T> {
    class Loading<T> : NetworkState<T>()
    class EmptyState<T> : NetworkState<T>()
    class NoInternet<T> : NetworkState<T>()
    class Error<T>(val error: Throwable, val code: Int) : NetworkState<T>()
    class Unauthorized<T> : NetworkState<T>()
    class Success<T>(val data: T?,val message : String) : NetworkState<T>()
}