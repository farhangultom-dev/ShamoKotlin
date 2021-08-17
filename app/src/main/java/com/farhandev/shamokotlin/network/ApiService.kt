package com.farhandev.shamokotlin.network

import com.farhandev.shamokotlin.network.response.login.LoginResponse
import com.farhandev.shamokotlin.network.response.signup.SignUpResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("/api/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("/api/register")
    fun signUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("username") username: String,
    ): Call<SignUpResponse>
}