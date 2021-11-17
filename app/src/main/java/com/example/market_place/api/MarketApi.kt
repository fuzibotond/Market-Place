package com.example.market_place.api

import com.example.eloadas8retrofit.utils.Constants
import com.example.market_place.model.LoginRequest
import com.example.market_place.model.LoginResponse
import com.example.market_place.model.ProductResponse
import retrofit2.Response
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String): ProductResponse
}