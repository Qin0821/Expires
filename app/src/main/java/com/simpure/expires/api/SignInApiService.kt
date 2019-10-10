package com.simpure.expires.api

import com.simpure.expires.model.ApiResponse
import com.simpure.expires.model.SignIn
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

interface SignInApiService  {

    @POST("api/sign/{id}")
    fun scanQRCode(
        @Path("id") id: Int
    ): Observable<ApiResponse<SignIn>>

    companion object {
        fun create(): SignInApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("http://simpure.org/")
                .build()

            return retrofit.create(SignInApiService::class.java)
        }
    }
}