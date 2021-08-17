package com.farhandev.shamokotlin.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.farhandev.shamokotlin.ShamoKotlin
import com.farhandev.shamokotlin.databinding.ActivityLoginBinding
import com.farhandev.shamokotlin.ui.home.HomeActivity
import com.farhandev.shamokotlin.ui.signup.SignUpActivity
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var activityLoginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        if (!ShamoKotlin.getApp().getToken().isNullOrEmpty()){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        dataDummy()

        activityLoginBinding.btnSignIn.setOnClickListener {
            val email = activityLoginBinding.edTextEmail.text.toString()
            val password = activityLoginBinding.edTextPassword.text.toString()
            when{
                email.isNullOrEmpty() -> {
                    activityLoginBinding.edTextEmail.error = "Email harus di isi"
                    activityLoginBinding.edTextEmail.requestFocus()
                }
                email.isNullOrEmpty() -> {
                    activityLoginBinding.edTextPassword.error = "Password harus di isi"
                    activityLoginBinding.edTextPassword.requestFocus()
                }else -> {
                loginViewModel.findLogin(email, password)
                goLogin()
            }
            }
        }


        with(activityLoginBinding){

            loginViewModel.isLoading.observe(this@LoginActivity,{ loading ->
                progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            })

            tvSignUp.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun goLogin() {
        loginViewModel.user.observe(this,{ user ->
            ShamoKotlin.getApp().setToken(user.accessToken)
            val gson = Gson()
            val json = gson.toJson(user.user)
            ShamoKotlin.getApp().setUser(json)
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        })

//        loginViewModel.login.observe(this, { login ->
//            if (login.status == "success"){
//
//            }else{
//                Toast.makeText(this,"Login ${login.status.toString()}",Toast.LENGTH_SHORT).show()
//            }
//        })
    }

    private fun dataDummy() {
        with(activityLoginBinding){
            edTextEmail.setText("jennie.kim@blackpink.co")
            edTextPassword.setText("12345678")
        }
    }
}