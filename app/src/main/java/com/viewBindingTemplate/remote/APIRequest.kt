package com.viewBindingTemplate.remote

import com.viewBindingTemplate.dto.models.response.*
import com.viewBindingTemplate.remote.NetworkUrls.CHANGE_PASSWORD
import com.viewBindingTemplate.remote.NetworkUrls.GET_FAMILY_BY_ID
import com.viewBindingTemplate.remote.NetworkUrls.LOGIN
import com.viewBindingTemplate.remote.NetworkUrls.MEDICAL_CONDITIONS
import com.viewBindingTemplate.remote.NetworkUrls.UPLOAD_MEDIA
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface APIRequest {

    @POST(LOGIN)
    suspend fun login(
        @Body requestBody: RequestBody,
    ): Response<BaseResponse<Any>>

    @PUT(CHANGE_PASSWORD)
    suspend fun changePassword(
        @Header("Authorization") auth: String?,
        @Body requestBody: RequestBody,
    ): Response<BaseResponse<Any>>

    @GET(MEDICAL_CONDITIONS)
    suspend fun getMedicalConditions(): Response<BaseResponse<List<Any>>>

    @GET("$GET_FAMILY_BY_ID{id}/")
    suspend fun getFamilyById(
        @Header("Authorization") auth: String?,
        @Path("id") familyId: Int,
    ): Response<BaseResponse<Any>>

    @Multipart
    @POST(UPLOAD_MEDIA)
    suspend fun uploadFile(
        @Part file: MultipartBody.Part?,
    ): Response<BaseResponse<Any>>

}
