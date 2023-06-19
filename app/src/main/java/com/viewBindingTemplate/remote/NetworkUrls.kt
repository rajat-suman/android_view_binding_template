package com.viewBindingTemplate.remote

object NetworkUrls {

    private const val API_VERSION = "api/"

    const val LOGIN: String = API_VERSION.plus("auth/login/")
    const val MEDICAL_CONDITIONS: String = API_VERSION.plus("get-all-medical-conditions/")
    const val CHANGE_PASSWORD: String = API_VERSION.plus("user/change-password/")
    const val GET_FAMILY_BY_ID: String = API_VERSION.plus("user/get-user-family-details-by-id/")
    const val UPLOAD_MEDIA: String = API_VERSION.plus("upload/media/")
}