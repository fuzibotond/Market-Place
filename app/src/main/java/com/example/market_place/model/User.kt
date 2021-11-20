package com.example.market_place.model


import com.squareup.moshi.JsonClass
import java.sql.Timestamp

//import com.google.gson.annotations.SerializedName

data class User(var username: String="", var first_name: String="", var last_name: String="", var password: String="", var email: String="", var phone_number: String="")

@JsonClass(generateAdapter = true)
data class LoginRequest (
    var username: String,
    var password: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse (
    var username: String,
    var email: String,
    var phone_number: Int,
    var token: String,
    var creation_time: Long,
    var refresh_time: Long
)

@JsonClass(generateAdapter = true)
data class RegisterRequest(
    var first_name: String,
    var last_name: String,
    var username: String,
    var email: String,
    var password: String,
    var phone_number: String,
)

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    var code: Int,
    var message:String,
    var creation_time:Long
)

@JsonClass(generateAdapter = true)
data class ResetPasswordRequest(
    var username:String,
    var email: String
)

@JsonClass(generateAdapter = true)
data class ResetPasswordResponse(
    var code: Int,
    var message: String,
    var timestamp: Long
)
// GSon converter
//data class LoginRequest (
//    @SerializedName("username")
//    var username: String,
//
//    @SerializedName("password")
//    var password: String
//)
//
//
//data class LoginResponse (
//    @SerializedName("username")
//    var username: String,
//
//    @SerializedName("email")
//    var email: String,
//
//    @SerializedName("phone_number")
//    var phone_number: Int,
//
//    @SerializedName("token")
//    var token: String,
//
//    @SerializedName("creation_time")
//    var creation_time: Long,
//
//    @SerializedName("refresh_time")
//    var refresh_time: Long
//)