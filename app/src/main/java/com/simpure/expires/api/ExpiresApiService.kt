package com.simpure.expires.api

import com.simpure.expires.model.ApiResponse
import com.simpure.expires.model.SignIn
import com.simpure.expires.model.BaseResponse
import com.simpure.expires.model.commodity.ExCommodityEntity
import com.simpure.expires.model.commodity.UserCommodityResponse
import com.simpure.expires.model.user.LoginResponseI
import com.simpure.expires.model.user.UserInfoResponseI
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ExpiresApiService {

    @POST("api/sign/{id}")
    fun scanQRCode(@Path("id") id: Int): Observable<ApiResponse<SignIn>>

    @GET("/ex/expires/exappuser/login")
    fun login(@Query("id") id: Long, @Query("phone") phone: Long, @Query("password") password: String): Observable<LoginResponseI>

    @GET("/ex/expires/exappuser/userInfo")
    fun getUserInfo(@Query("token") token: String): Observable<UserInfoResponseI>

    @GET("/ex/app/expires/commodity/userList")
    fun getUserCommodity(@Query("token") token: String): Observable<UserCommodityResponse>

    @POST("/ex/app/expires/commodity")
    fun addCommodity(@Body commodity: ExCommodityEntity): Observable<BaseResponse>

    companion object {
        fun create(): ExpiresApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl("http://72.167.220.164:8282")
                .build()

            return retrofit.create(ExpiresApiService::class.java)
        }
    }
}