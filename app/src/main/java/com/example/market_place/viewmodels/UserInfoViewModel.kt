package com.example.market_place.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.User
import com.example.market_place.repository.Repository
import kotlinx.coroutines.launch


class UserInfoViewModel(private val repository: Repository) : ViewModel() {
    var user = MutableLiveData<User>()
    var token: MutableLiveData<String> = MutableLiveData()

    init{
        getUserInfo()
    }

    fun getUserInfo() {
        viewModelScope.launch {
            try {
                Log.d("xxx", MarketPlaceApplication.user!!.username)
                val result =
                    repository.getUserInfo(MarketPlaceApplication.user!!.username)
                user.value = User(result.data[0].username,"", "","",result.data[0].email,result.data[0].phone_number.toString(),result.data[0].is_activated,result.data[0].creation_time)
//                user.value?.username = result.data[0].username
//                user.value?.email = result.data[0].email
//                user.value?.phone_number = result.data[0].phone_number.toString()
//                user.value?.is_activated = result.data[0].is_activated
//                user.value?.creation_time = result.data[0].creation_time
                MarketPlaceApplication.user = user.value
                Log.d("xxx", "UserInfoViewModel - #profil:  ${user.value}")
            }catch(e: Exception){
                Log.d("xxx", "UserInfoViewModel exception: ${e.toString()}")
            }
        }
    }
}