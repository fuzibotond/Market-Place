package com.example.market_place.viewmodels


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market_place.model.RegisterRequest
import com.example.market_place.model.User
import com.example.market_place.repository.Repository


class RegisterViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var user = MutableLiveData<User>()
    var code = MutableLiveData<Int>()
    init {
        user.value = User()
    }


    suspend fun register() {
        val request =
            RegisterRequest(phone_number = user.value!!.phone_number, first_name = user.value!!.first_name, last_name = user.value!!.last_name, email = user.value!!.email,  password = user.value!!.password, username = user.value!!.email.split('@')[0])
        try {
            val result = repository.register(request)
            code.value = result.code
            Toast.makeText(context,"${result.message} Please activate your User!",Toast.LENGTH_SHORT).show()
            Log.d("xxx", "RegisterViewModel - response code:${result.code} - message: ${result.message} ")
        } catch (e: Exception) {
            Log.d("xxx", "RegisterViewModel - exception: ${e.toString()}")
        }
    }
}
