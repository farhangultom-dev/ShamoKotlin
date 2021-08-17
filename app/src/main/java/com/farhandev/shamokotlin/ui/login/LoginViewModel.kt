package com.farhandev.shamokotlin.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farhandev.shamokotlin.network.ApiConfig
import com.farhandev.shamokotlin.network.response.login.Data
import com.farhandev.shamokotlin.network.response.login.LoginResponse
import com.farhandev.shamokotlin.network.response.login.Meta
import com.farhandev.shamokotlin.network.response.login.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    private val _login = MutableLiveData<Meta>()
    val login: LiveData<Meta> = _login

    private val _user = MutableLiveData<Data>()
    val user: LiveData<Data> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findLogin(email:String,password:String) {
        _isLoading.value = true
        val client = ApiConfig.getInstance().getApi()?.login(email,password)
        client?.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _login.value = response.body()?.meta
                    _user.value = response.body()?.data
                }else{
                    _isLoading.value = false
                    Log.e("LoginViewModel","onFailure: ${response.body()?.meta?.message.toString()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("LoginViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }
}