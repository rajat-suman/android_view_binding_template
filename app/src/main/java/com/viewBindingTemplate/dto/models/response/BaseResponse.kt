package com.viewBindingTemplate.dto.models.response

data class BaseResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null,
    val totalPages: Int? = null
)
