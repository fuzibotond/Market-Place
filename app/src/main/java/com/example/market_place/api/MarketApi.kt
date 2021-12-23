package com.example.market_place.api

import com.example.market_place.MarketPlaceApplication
import com.example.market_place.utils.Constants
import com.example.market_place.model.*
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import retrofit2.Response
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST(Constants.RESET_PASSWORD_URL)
    suspend fun resetPassword(@Body request: ResetPasswordRequest): ResetPasswordResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String, @Header("limit") limit:Int, @Header("skip") skip:Int): ProductResponse

    @GET(Constants.GET_USER_INFO)
    suspend fun getUserInfo(@Header("username") username: String): UserInfoResponse

    @POST(Constants.UPDATE_USER_DATA)
    suspend fun updateUserData(@Header("token") token: String, @Body request: UpdateUserDataRequest): UpdateUserDataResponse

    @GET(Constants.GET_REFRESH_TOKEN)
    suspend fun getRefreshToken(@Header("token") token: String): RefreshTokenResponse

    @Multipart
    @POST(Constants.ADD_PRODUCT)
    suspend fun addProduct(
        @Header("token") token: String?,
        @Part("title" ) title: String?,
        @Part("description" ) description: String?,
        @Part("price_per_unit" ) price_per_unit: String?,
        @Part("units" ) units: String?,
        @Part("is_active" ) is_active: Boolean?,
        @Part("rating" ) rating: Double?,
        @Part("amount_type" ) amount_type: String?,
        @Part("price_type" ) price_type: String?,
    ): AddProductResponse

    @POST(Constants.REMOVE_PRODUCT)
    suspend fun removeProduct(@Header("token") token: String, @Query("product_id") product_id: String): RemoveProductResponse

    @GET(Constants.GET_ORDERS)
    suspend fun getOrders(@Header("token") token: String, @Header("limit") limit:Int): OrderResponse

    @Multipart
    @POST(Constants.ADD_ORDER)
    suspend fun addOrder(
        @Header("token") token: String?,
        @Part("title" ) title: String?,
        @Part("description" ) description: String?,
        @Part("price_per_unit" ) price_per_unit: String?,
        @Part("units" ) units: String?,
        @Part("owner_username" ) owner_username: String?,
    ): OrderResponseCode

    @POST(Constants.UPDATE_PRODUCT)
    suspend fun updateProduct(
        @Header("token") token:String,
        @Body request: UpdateProductRequest,
        @Query("product_id") product_id: String): UpdateProductResponse

    @POST(Constants.UPDATE_ORDER)
    suspend fun updateOrder(
        @Header("token") token:String,
        @Body request: UpdateOrderRequest,
        @Query("order_id") order_id: String): UpdateOrderResponse
    @POST(Constants.REMOVE_ORDER)
    suspend fun removeOrder(@Header("token") token: String, @Query("order_id") order_id: String): RemoveOrderResponse


    @POST(Constants.ADD_MESSAGE_TO_PRODUCT)
    suspend fun addMessageToProduct(
        @Header("token") token: String?,
        @Header("product_id") product_id: String?,
        @Body message: AddMessageRequest?,
    ): AddMessageToProductResponse

    @GET(Constants.GET_MESSAGE_FOR_PRODUCTS)
    suspend fun getALlMessagesForProduct(@Header("token") token: String,  @Header("product_id") product_id: String?,): GetMessagesResponse

    @POST(Constants.ADD_MESSAGE_TO_ORDER)
    suspend fun addMessageToOrder(
        @Header("token") token: String?,
        @Header("order_id") order_id: String?,
        @Body message: AddMessageRequest?,
    ): AddMessageToOrderResponse
    @GET(Constants.GET_MESSAGE_FOR_ORDERS)
    suspend fun getALlMessagesForOrder(@Header("token") token: String,  @Header("order_id") order_id: String?,): GetMessagesResponse

}
