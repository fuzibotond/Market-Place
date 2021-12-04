package com.example.market_place.repository

import com.example.market_place.api.RetrofitInstance
import com.example.market_place.model.*

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }
    suspend fun register(request: RegisterRequest): RegisterResponse {
        return RetrofitInstance.api.register(request)
    }

    suspend fun resetPassword(request: ResetPasswordRequest): ResetPasswordResponse {
        return RetrofitInstance.api.resetPassword(request)
    }

    suspend fun getProducts(token: String): ProductResponse {
        return RetrofitInstance.api.getProducts(token)
    }

    suspend fun getUserInfo(username: String): UserInfoResponse {
        return RetrofitInstance.api.getUserInfo(username)
    }

    suspend fun updateUserData(token:String, request: UpdateUserDataRequest): UpdateUserDataResponse {
        return RetrofitInstance.api.updateUserData(token, request)
    }

    suspend fun getRefreshToken(token: String): RefreshTokenResponse {
        return RetrofitInstance.api.getRefreshToken(token)
    }
}