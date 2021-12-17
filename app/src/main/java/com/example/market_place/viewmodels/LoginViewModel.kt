package com.example.market_place.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.LoginRequest
import com.example.market_place.model.User
import com.example.market_place.repository.Repository


class LoginViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token: MutableLiveData<String> = MutableLiveData()
    var creation_time: MutableLiveData<Long> = MutableLiveData()
    var refresh_time: MutableLiveData<Long> = MutableLiveData()
    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }

//    fun login() {
//        viewModelScope.launch {
//            val request =
//                LoginRequest(username = user.value!!.username, password = user.value!!.password)
//            try {
//                val result = repository.login(request)
//                MyApplication.token = result.token
//                token.value = result.token
//                Log.d("xxx", "MyApplication - token:  ${MyApplication.token}")
//            }catch(e: Exception){
//                Log.d("xxx", "MainViewModel - exception: ${e.toString()}")
//            }
//        }
//    }

    suspend fun login():Boolean {
        val request =
            LoginRequest(username = user.value!!.username, password = user.value!!.password)
//            LoginRequest("vitaminos@tojas.com", "Pass12.")
        try {
            val result = repository.login(request)
            MarketPlaceApplication.token = result.token
            user.value?.username = result.username
            MarketPlaceApplication.username = user.value!!.username
            token.value = result.token
            creation_time.value = result.creation_time
            refresh_time.value = result.refresh_time
            MarketPlaceApplication.refresh_time = result.refresh_time
            MarketPlaceApplication.creation_time = result.creation_time

            Log.d("xxx", "MyApplication - token:  ${MarketPlaceApplication.token}")
            return true
        } catch (e: Exception) {
            if (e.message?.split(' ')!![1]?.toInt() == 302 ){
                Toast.makeText(this.context, "No such a user! Try again or register!", Toast.LENGTH_SHORT).show()
            }

            Log.d("xxx", "LoginViewModel - exception: ${e.toString()}")
            return false
        }
    }
}
