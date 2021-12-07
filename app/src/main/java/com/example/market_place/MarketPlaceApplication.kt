package com.example.market_place


import android.app.Application
import com.example.market_place.model.User
import com.example.market_place.utils.SessionManager

class MarketPlaceApplication: Application(){
    companion object{
        var token: String =""
        var creation_time:Long=0
        var refresh_time:Long=0
        var username: String=""
        var user_phone_number: String=""
        var user_email: String=""
    }
}