package com.example.market_place.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.LoginRequest
import com.example.market_place.model.UpdateUserDataRequest
import com.example.market_place.model.User
import com.example.market_place.repository.Repository


class UpdateUserInfoViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }

    suspend fun updateUserData() {
        val request =
            UpdateUserDataRequest(user.value!!.phone_number, user.value!!.username, user.value!!.email)
//            UpdateUserDataRequest("111122", "vitaminos@tojas.com", "vitaminos@tojas.com")
        try {
            val result = repository.updateUserData(MarketPlaceApplication.token, request)
            MarketPlaceApplication.token = result.updatedData.token
            user.value?.username = result.updatedData.username
            user.value?.email = result.updatedData.email
            user.value?.phone_number = result.updatedData.phone_number.toString()
            user.value?.is_activated = result.updatedData.is_activated
            user.value?.creation_time = result.updatedData.creation_time
            Log.d("xxx", "Update user data - code: ${result.updatedData}")
            MarketPlaceApplication.user = user.value
            Log.d("xxx", "MyApplication update user data - code: ${result.code} token:  ${MarketPlaceApplication.user}")
        } catch (e: Exception) {
            Log.d("xxx", "LoginViewModel - exception: ${e.toString()}")
        }
    }
}
