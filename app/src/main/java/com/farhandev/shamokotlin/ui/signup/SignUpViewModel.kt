package com.farhandev.shamokotlin.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farhandev.shamokotlin.network.ApiConfig
import com.farhandev.shamokotlin.network.response.signup.Meta
import com.farhandev.shamokotlin.network.response.signup.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel: ViewModel() {
    private val _register = MutableLiveData<Meta>()
    val register: LiveData<Meta> = _register

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun signUp(email:String,password:String,name:String,username:String){
        _isLoading.value = true
        val client = ApiConfig.getInstance().getApi()?.signUp(email, password, name, username)
        client?.enqueue(object : Callback<SignUpResponse>{
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if(response.isSuccessful){
                    _register.value = response.body()?.meta
                }else{
                    Log.e("SignUpViewModel", "onFailure: ${response.body()?.meta?.status.toString()}")
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.e("SignUpModel", "onFailure: ${t.message.toString()}")
            }

        })
    }
}