package com.viewBindingTemplate.sample.ui.sheets.logout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viewBindingTemplate.customclasses.datastore.DataStoreUtil
import com.viewBindingTemplate.remote.APIRequest
import com.viewBindingTemplate.remote.Api
import com.viewBindingTemplate.remote.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutVM @Inject constructor(
    private val apiRequest: APIRequest,
    private val apiCall: Api,
    private val dataStore: DataStoreUtil,
) : ViewModel() {

    private var _logoutFlow = Channel<NetworkState<Any>?> {}
    val logoutFlow: Flow<NetworkState<Any>?> get() = _logoutFlow.receiveAsFlow()

    fun logout() = dataStore.readAuthToken {
        viewModelScope.launch {
            apiCall.handleAsyncCall(sharedFlow = _logoutFlow, requestHandler = {
                apiRequest.getFamilyById("Auth", 10)
            }, showLoader = true)
        }
    }

}