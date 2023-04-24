package com.viewBindingTemplate.remote

object NetworkUrls {

    private const val API_VERSION = "api/"

    const val REGISTER: String = API_VERSION.plus("auth/sign-up/")
    const val LOGIN: String = API_VERSION.plus("auth/login/")
    const val SEND_OTP: String = API_VERSION.plus("user/send-otp-to-old-user/")
    const val VERIFY_OTP: String = API_VERSION.plus("user/verify-otp/")
    const val FORGOT_PASSWORD: String = API_VERSION.plus("user/forgot-password/")
    const val FORGOT_PASSWORD_OTP_VERIFY: String = API_VERSION.plus("user/forgot-verify-otp/")
    const val MEDICAL_CONDITIONS: String = API_VERSION.plus("get-all-medical-conditions/")
    const val PROFILE_SETUP: String = API_VERSION.plus("user/add-user-details/")
    const val UPDATE_PROFILE: String = API_VERSION.plus("user/update-user-details/")
    const val RESET_PASSWORD: String = API_VERSION.plus("user/update-password/")
    const val CHANGE_PASSWORD: String = API_VERSION.plus("user/change-password/")
    const val LOGOUT: String = API_VERSION.plus("auth/logout/")

    const val DASHBOARD: String = API_VERSION.plus("product/get-home-page-data/")
    const val CERTIFICATE: String = API_VERSION.plus("certificate/get-all-certificate/")
    const val ESCRIPT: String = API_VERSION.plus("escript/get-escript-category/")
    const val ESCRIPT_POINTS: String = API_VERSION.plus("escript/get-escript-points/")

    const val CMS: String = API_VERSION.plus("cms/get-cms/")

    const val GET_USER_DETAIL: String = API_VERSION.plus("user/get-user-details-by-token/")
    const val ADD_FAMILY: String = API_VERSION.plus("user/create-user-family-details/")
    const val GET_FAMILY: String = API_VERSION.plus("user/get-all-family-details/")
    const val GET_FAMILY_BY_ID: String = API_VERSION.plus("user/get-user-family-details-by-id/")
    const val UPDATE_FAMILY_BY_ID: String = API_VERSION.plus("user/update-user-family-details-by-id/")


    const val COMMON_QUES : String = API_VERSION.plus("common/get-all-ques/")
    const val COLLECTION_QUES : String = API_VERSION.plus("collection/get-all-collection/")
    const val DIGITAL_QUES : String = API_VERSION.plus("question/get-all-question/")

}