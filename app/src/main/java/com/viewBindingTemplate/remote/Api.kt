package com.viewBindingTemplate.remote

import android.util.Log
import com.viewBindingTemplate.R
import com.viewBindingTemplate.controller.Controller
import com.viewBindingTemplate.customclasses.datastore.DataStoreUtil
import com.viewBindingTemplate.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class Api @Inject constructor(
    private val dataStoreUtil: DataStoreUtil,
) {

    suspend fun <T> handleAsyncCall(
        sharedFlow: MutableSharedFlow<NetworkState<T>?>,
        requestHandler: RequestHandler<T>,
        showLoader: Boolean = false,
    ) {
        val context = Controller.context?.get()

        if (!ConnectionManager.isInternetConnected()) {
            sharedFlow.emit(NetworkState.NoInternet())
        }
        hideSoftKeyBoard()
        if (showLoader) context?.showProgress()

        sharedFlow.emit(NetworkState.Loading())

        try {
            val response = withContext(Dispatchers.IO) {
                requestHandler.sendRequest()
            }
            if (showLoader) hideProgress()
            when {
                response.isSuccessful -> response.body()?.let { user ->
                    (user.message ?: context?.getString(
                        R.string.ok
                    ))?.let {
                        sharedFlow.emit(
                            NetworkState.Success(
                                user.data, it
                            )
                        )
                    }
                }
                response.code() == 401 -> {
                    context?.let { sessionExpired(it, dataStoreUtil) }
                    sharedFlow.emit(
                        NetworkState.Error(
                            Throwable(response.message()), code = 401
                        )
                    )
                }
                else -> {
                    val message = response.errorBody()?.string()?.errorMessage()
                    showToast(message ?: "")
                    sharedFlow.emit(
                        NetworkState.Error(
                            Throwable(message), code = response.code()
                        )
                    )
                }
            }
        } catch (e: Exception) {
            val message = e.errorMessage()
            if (showLoader) hideProgress()
            message?.let { showToast(it) }
            sharedFlow.emit(NetworkState.Error(Throwable(message), code = -1))
        }
    }

    private fun Any?.errorMessage(): String? {
        return when (this) {
            is Exception -> parseErrorMessage(this.message)
            is Throwable -> parseErrorMessage(this.message)
            else -> {
                parseErrorMessage(this.toString())
            }
        }
    }

    private fun parseErrorMessage(response: String?): String? {
        if (response.isNullOrEmpty()) return ""
        if (isJson(response).not()) {
            Log.e("API_ERROR", response)
            return null
        }
        val jsonObject = JSONObject(response)
        return when {
            jsonObject.has("message") -> jsonObject.getString("message")
            jsonObject.has("error") -> jsonObject.getString("error")
            jsonObject.has("errors") -> {
                val array = jsonObject.getJSONArray("errors")
                if (array.length() > 0) {
                    array.getJSONObject(0)?.let {
                        if (it.has("message")) return it.getString("message")
                    }
                }
                response
            }
            else -> {
                response
            }
        }
    }

    private fun isJson(str: String): Boolean {
        return try {
            JSONObject(str)
            true
        } catch (e: Exception) {
            false
        }
    }


}