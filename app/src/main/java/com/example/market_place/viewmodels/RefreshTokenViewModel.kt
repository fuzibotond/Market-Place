package com.example.market_place.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.Product
import com.example.market_place.repository.Repository
import kotlinx.coroutines.launch

class RefreshTokenViewModel(private val repository: Repository) : ViewModel() {
    var token = MutableLiveData<String>()
    var creation_time = MutableLiveData<Long>()
    init{
        Log.d("xxx", "Refresh Token constructor - Token: ${MarketPlaceApplication.token}")
        getRefreshToken()
    }

    fun getRefreshToken() {
        viewModelScope.launch {
            try {
                val result =
                    repository.getRefreshToken(MarketPlaceApplication.token)
                token.value = result.token
                creation_time.value = result.creation_time
                Log.d("xxx", " - #token:  ${result.token}")
            }catch(e: Exception){
                Log.d("xxx", "ListViewMofdel exception: ${e.toString()}")
            }
        }
    }
}