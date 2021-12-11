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



    fun getUserInfo(username:String) {
        viewModelScope.launch {
            try {
                val result =
                    repository.getUserInfo(username)
                user.value = User(result.data[0].username,"", "","",result.data[0].email,result.data[0].phone_number.toString(),result.data[0].is_activated,result.data[0].creation_time)
                Log.d("xxx", "UserInfoViewModel - #profil:  ${user.value}")
            }catch(e: Exception){
                Log.d("xxx", "UserInfoViewModel exception: ${e.toString()}")
            }
        }

    }
}