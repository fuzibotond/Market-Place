package com.example.market_place.api

import com.example.eloadas8retrofit.utils.Constants
import com.example.market_place.model.*
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
}