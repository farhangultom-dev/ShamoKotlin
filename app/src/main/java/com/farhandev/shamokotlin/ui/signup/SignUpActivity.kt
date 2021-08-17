package com.farhandev.shamokotlin.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.farhandev.shamokotlin.R
import com.farhandev.shamokotlin.databinding.ActivitySignUpBinding
import com.farhandev.shamokotlin.ui.login.LoginActivity

class SignUpActivity : AppCompatActivity() {
    private val signUpViewModel: SignUpViewModel by viewModels()
    private lateinit var activitySignUpBinding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(activitySignUpBinding.root)
        DataDummy()

        with(activitySignUpBinding){
            tvSignIn.setOnClickListener {
                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            }
            btnSignUp.setOnClickListener {
                val name = edTextFullName.text.toString()
                val username = edTextUsername.text.toString()
                val email = edTextEmail.text.toString()
                val password = edTextPassword.text.toString()

                when {
                    name.isNullOrEmpty() -> {
                        edTextFullName.error = "Silahkan isi nama anda"
                        edTextFullName.requestFocus()
                    }
                    username.isNullOrEmpty() -> {
                        edTextUsername.error = "Silahkan isi username anda"
                        edTextUsername.requestFocus()
                    }
                    email.isNullOrEmpty() -> {
                        edTextEmail.error = "Silahkan isi email anda"
                        edTextEmail.requestFocus()
                    }
                    password.isNullOrEmpty() -> {
                        edTextPassword.error = "Silahkan isi password anda"
                        edTextPassword.requestFocus()
                    }
                    else -> {
                        signUpViewModel.signUp(email,password,name,username)
                        goSignUp()
                    }
                }
            }

            signUpViewModel.isLoading.observe(this@SignUpActivity,{ loading ->
                progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            })
        }
    }

    private fun DataDummy() {
        with(activitySignUpBinding){
            edTextEmail.setText("muhfarhan3@blackpink.co")
            edTextPassword.setText("12345678")
            edTextUsername.setText("muhfarhan3")
            edTextFullName.setText("Muh Farhan3")
        }
    }

    private fun goSignUp() {
        signUpViewModel.register.observe(this@SignUpActivity,{ register ->
            if (register.status == "success"){
                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                Toast.makeText(this@SignUpActivity, "Anda berhasil terdaftar, silahkan login",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@SignUpActivity, "Gagal, silahkan daftar ulang lagi",Toast.LENGTH_SHORT).show()
            }
        })
    }
}