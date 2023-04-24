package com.viewBindingTemplate.remote

import com.viewBindingTemplate.R
import com.viewBindingTemplate.utils.showToast
import okhttp3.CacheControl
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

object ConnectionInterceptor {

    val interceptor = Interceptor {
        var request = it.request()
        request = request.newBuilder().apply {
            header(HttpStatusCode.ACCEPT, HttpStatusCode.APPLICATION_JSON)
            header(HttpStatusCode.CONTENT_TYPE, HttpStatusCode.APPLICATION_JSON)
            method(request.method, request.body)
            val cacheControl = if (ConnectionManager.isInternetConnected()) {
                CacheControl.Builder().maxAge(5, TimeUnit.MINUTES).build()
            } else {
                showToast(R.string.no_internet_connection)
                CacheControl.Builder().onlyIfCached().maxStale(7, TimeUnit.DAYS).build()
            }
            cacheControl(cacheControl)
        }.build()
        it.proceed(request)
    }

}

