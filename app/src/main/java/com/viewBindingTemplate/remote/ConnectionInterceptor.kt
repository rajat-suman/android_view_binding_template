package com.viewBindingTemplate.remote

import com.viewBindingTemplate.utils.showToast
import com.viewbinding.R
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.CacheControl
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

object ConnectionInterceptor {

    val interceptor = Interceptor {
        var request = it.request()

        request = request.newBuilder().apply {
            /* if (request.method.equals("POST", true) || request.method.equals("PUT",true)) {
                 if (request.body != null) {
                     val buffer = okio.Buffer()
                     request.body?.writeTo(buffer);
                     val requestBodyJson = buffer.readUtf8()
                     runBlocking {
                         val abc = async{
                             AESUtil.secKeyEncryptWithAppKey(requestBodyJson)
                         }
                         val json=  abc.await()
                         loggerError(json.toString())
                         request = request.newBuilder().method(
                             request.method,
                             json?.getJsonRequestBody(request.body?.contentType())
                         ).build()
                     }
                 }

             }
             */
            runBlocking {
                val deferred = async {
                    AESUtil.secKeyEncryptWithAppKey()
                }
                val map = deferred.await()
                map?.let { aesMap ->
                    header(AESUtil.SEK, aesMap[AESUtil.SEK] ?: "")
                    header(AESUtil.HASH, aesMap[AESUtil.HASH] ?: "")
                }
            }

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

