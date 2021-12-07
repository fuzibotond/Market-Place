package com.example.market_place.api

import com.example.market_place.utils.Constants
import com.example.market_place.model.*
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import retrofit2.Response
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST(Constants.RESET_PASSWORD_URL)
    suspend fun resetPassword(@Body request: ResetPasswordRequest): ResetPasswordResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String): ProductResponse

    @GET(Constants.GET_USER_INFO)
    suspend fun getUserInfo(@Header("username") username: String): UserInfoResponse

    @POST(Constants.UPDATE_USER_DATA)
    suspend fun updateUserData(@Header("token") token: String, @Body request: UpdateUserDataRequest): UpdateUserDataResponse

    @GET(Constants.GET_REFRESH_TOKEN)
    suspend fun getRefreshToken(@Header("token") token: String): RefreshTokenResponse

    @POST(Constants.ADD_PRODUCT)
    suspend fun addProduct(@Header("token") token: String, @Body request: AddProductRequest ): AddProductResponse

    @POST(Constants.REMOVE_PRODUCT)
    suspend fun removeProduct( @Field("product_id") product_id:String ): RemoveProductResponse
}