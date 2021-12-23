package com.example.market_place.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.LoginRequest
import com.example.market_place.model.ResetPasswordRequest
import com.example.market_place.model.ResetPasswordResponse
import com.example.market_place.model.User
import com.example.market_place.repository.Repository


class ResetPasswordViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var result: MutableLiveData<ResetPasswordResponse> = MutableLiveData()
    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }


    suspend fun resetPassword() {
        val request =
            ResetPasswordRequest(username = user.value!!.username,email = user.value!!.email)
        try {
            val result = repository.resetPassword(request)
            this.result.value = result
            Log.d("xxx", "ResetPasswordViewModel ${result.message} - token:  ${MarketPlaceApplication.token}")
        } catch (e: Exception) {
            Log.d("xxx", "ResetPasswordViewModel - exception: ${e.toString()}")
        }
    }
}
