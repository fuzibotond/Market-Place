package com.example.market_place.model


import com.squareup.moshi.JsonClass
import java.sql.Timestamp

//import com.google.gson.annotations.SerializedName

data class User(var username: String="", var first_name: String="", var last_name: String="", var password: String="", var email: String="", var phone_number: String="", var is_activated: Boolean=false, var creation_time: Long=0,)
data class UserInfo(var username: String="", var phone_number: Long=0, var email: String="", var is_activated: Boolean=false, var creation_time: Long=0)
data class UpdatedData(var username: String="", var phone_number: Long=0, var email: String="", var firebase_token:String="token", var is_activated: Boolean=false, var creation_time: Long=0, var token: String="")
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


@JsonClass(generateAdapter = true)
data class UserInfoResponse (
    var code: Int,
    var data: Array<UserInfo>,
    var timestamp: Long
)

@JsonClass(generateAdapter = true)
data class UpdateUserDataResponse (
    var code: Int,
    var updatedData: UpdatedData,
    var timestamp: Long,
)

@JsonClass(generateAdapter = true)
data class UpdateUserDataRequest (
    var phone_number: String,
    var username: String,
    var email: String,
)
@JsonClass(generateAdapter = true)
data class RefreshTokenResponse (
    var token: String,
    var creation_time: Long,
    var refresh_time: Long,
)