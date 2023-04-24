package com.viewBindingTemplate.remote

import com.viewBindingTemplate.dto.models.response.BaseResponse
import retrofit2.Response

fun interface RequestHandler<T> {

    suspend fun sendRequest(): Response<BaseResponse<T>>

}