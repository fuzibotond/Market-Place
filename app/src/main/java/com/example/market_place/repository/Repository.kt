package com.example.market_place.repository

import com.example.market_place.api.RetrofitInstance
import com.example.market_place.model.LoginRequest
import com.example.market_place.model.LoginResponse
import com.example.market_place.model.ProductResponse

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun getProducts(token: String): ProductResponse {
        return RetrofitInstance.api.getProducts(token)
    }
}