package com.farhandev.shamokotlin

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.multidex.MultiDexApplication
import com.farhandev.shamokotlin.network.ApiConfig

class ShamoKotlin: MultiDexApplication() {

    companion object{
        lateinit var instance: ShamoKotlin

        fun getApp(): ShamoKotlin{
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getPreferences(): SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(this)
    }

    fun setToken(token:String){
        getPreferences().edit().putString("PREFERENCES_TOKEN", token).apply()
        ApiConfig.getInstance().buildRetrofitClient(token)
    }

    fun getToken(): String?{
        return getPreferences().getString("PREFERENCES_TOKEN", null)
    }

    fun setUser(user: String){
        getPreferences().edit().putString("PREFERENCES_USER", user).apply()
        ApiConfig.getInstance().buildRetrofitClient(user)
    }

    fun getUser(): String?{
        return getPreferences().getString("PREFERENCES_USER",null)
    }
}