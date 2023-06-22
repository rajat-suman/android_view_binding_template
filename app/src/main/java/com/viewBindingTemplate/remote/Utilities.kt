package com.viewBindingTemplate.remote

import android.util.Log
import android.webkit.MimeTypeMap
import com.viewBindingTemplate.customclasses.coroutines.mainThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object Utilities {

    /**
     * Converts an object to a JSON request body.
     * @return The object converted to a JSON request body.
     */
    fun Any.getJsonRequestBody() =
        this.toString().toRequestBody("application/json".toMediaTypeOrNull())

    /**
     * Converts an object to a form data request body.
     * @return The object converted to a form data request body.
     */
    fun Any.getFormDataBody() =
        this.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())

    fun String.toBearer() = "Bearer ".plus(this)

    fun String?.getPartFromFile(param: String): MultipartBody.Part? {
        return if (!isNullOrBlank()) {
            val file = File(this)
            val reqFile = file.asRequestBody(this.getMimeType().toMediaTypeOrNull())
            MultipartBody.Part.createFormData(param, file.name, reqFile)
        } else null
    }

    fun File?.getPartFromFile(param: String): MultipartBody.Part? {
        return if (this?.exists() == true) {
            val reqFile = this.asRequestBody(this.absolutePath.getMimeType().toMediaTypeOrNull())
            MultipartBody.Part.createFormData(param, this.name, reqFile)
        } else null
    }

    private fun String.getMimeType(): String {
        val extension = MimeTypeMap.getFileExtensionFromUrl(this) ?: return "image/jpg"
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "image/jpg"
    }

    inline fun <T> Flow<NetworkState<T>?>.collectWithState(
        crossinline onLoading: () -> Unit = {},
        crossinline onError: (message: Throwable, code: Int) -> Unit = { throwable: Throwable, i: Int ->
            Log.e("API_ERROR", "Code $i and message is${throwable.message ?: ""}")
        },
        crossinline onNoInternet: () -> Unit = {},
        crossinline onSuccess: (data: T?, message: String) -> Unit,
        crossinline onUnAuthorized: () -> Unit = {},
        crossinline onOther: (state: NetworkState<T>?) -> Unit = {},
    ) {
        mainThread {
            collectLatest {
                if (it == null) return@collectLatest
                when (it) {
                    is NetworkState.Loading<T> -> onLoading()
                    is NetworkState.Error -> onError(
                        it.error, it.code
                    )

                    is NetworkState.Unauthorized<T> -> onUnAuthorized()
                    is NetworkState.NoInternet<T> -> onNoInternet()
                    is NetworkState.Success<T> -> onSuccess(it.data, it.message)
                    else -> onOther(it)
                }
            }
        }
    }


}