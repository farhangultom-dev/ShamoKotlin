package com.farhandev.shamokotlin.network

import com.farhandev.shamokotlin.BuildConfig
import com.farhandev.shamokotlin.ShamoKotlin
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiConfig {
    private var client: Retrofit? = null
    private var endpoint: ApiService? = null
    companion object{
        private var mInstance: ApiConfig = ApiConfig()
        @Synchronized
        fun getInstance():ApiConfig{
            return mInstance
        }
//        fun getApiService(): ApiService{
//            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
//            val retrofit = Retrofit.Builder().baseUrl("https://shamo-backend.buildwithangga.id")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build()
//            return retrofit.create(ApiService::class.java)
//        }
    }

    fun getApi(): ApiService?{
        if (endpoint == null){
            endpoint = client?.create(ApiService::class.java)
        }
        return endpoint
    }

    init {
        buildRetrofitClient()
    }

    private fun buildRetrofitClient() {
        val token = ShamoKotlin.getApp().getToken()
        buildRetrofitClient(token)
    }

    fun buildRetrofitClient(token: String?) {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(2,TimeUnit.MINUTES)
        builder.readTimeout(2,TimeUnit.MINUTES)

        if (BuildConfig.DEBUG){
            var interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        if (token != null){
            builder.addInterceptor(getInterceptorWithHeader("Authorization", "Bearer $token"))
        }

        val okHttpClient = builder.build()
        client = Retrofit.Builder()
            .baseUrl("https://shamo-backend.buildwithangga.id")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        endpoint = null
    }

    private fun getInterceptorWithHeader(headerName: String, headerValue: String): Interceptor {
        val header = HashMap<String,String>()
        header.put(headerName, headerValue)
        return getInterceptorWithHeader(header)
    }

    private fun getInterceptorWithHeader(headers: Map<String, String>): Interceptor{
        return Interceptor {
            val original = it.request()
            val builder = original.newBuilder()
            for ((key, value) in headers){
                builder.addHeader(key,value)
            }
            builder.method(original.method, original.body)
            it.proceed(builder.build())
        }
    }
}