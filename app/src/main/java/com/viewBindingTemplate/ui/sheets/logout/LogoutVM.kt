package com.viewBindingTemplate.ui.sheets.logout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viewBindingTemplate.customclasses.datastore.DataStoreUtil
import com.viewBindingTemplate.remote.APIRequest
import com.viewBindingTemplate.remote.Api
import com.viewBindingTemplate.remote.NetworkState
import com.viewBindingTemplate.remote.Utilities.toBearer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutVM @Inject constructor(
    private val apiRequest: APIRequest,
    private val apiCall: Api,
    val dataStore: DataStoreUtil,
) : ViewModel() {

    private var _logoutFlow = MutableSharedFlow<NetworkState<Any>?>()
    val logoutFlow: SharedFlow<NetworkState<Any>?> get() = _logoutFlow

    fun logout() = dataStore.readAuthToken {
        viewModelScope.launch {
            apiCall.handleAsyncCall(sharedFlow = _logoutFlow, requestHandler = {
                apiRequest.logout(it?.toBearer())
            }, showLoader = true)
        }
    }

}