package com.viewBindingTemplate.remote

import com.viewBindingTemplate.dto.models.response.*
import com.viewBindingTemplate.remote.NetworkUrls.LOGOUT
import retrofit2.Response
import retrofit2.http.*

interface APIRequest {
    @POST(LOGOUT)
    suspend fun logout(
        @Header("Authorization") auth: String?,
    ): Response<BaseResponse<Any>>
}
