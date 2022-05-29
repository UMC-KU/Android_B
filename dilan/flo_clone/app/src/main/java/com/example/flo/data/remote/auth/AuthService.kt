package com.example.flo.data.remote.auth

import android.util.Log
import com.example.flo.ui.login.LoginView
import com.example.flo.ui.signup.SignUpView
import com.example.flo.data.entities.User
import com.example.flo.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun signUp(user: User) {
        val signUpService = getRetrofit().create(AuthRetrofitInterface::class.java)
        signUpService.signUp(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {


                val resp: AuthResponse = response.body()!!
                Log.d("SIGNUP/SUCCESS", resp.toString())

                when (val code = resp.code) {
                    1000 -> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }
        })
        Log.d("SIGNUP", "HELLO")
    }

    fun login(user: User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                val loginResponse: AuthResponse = response.body()!!
                Log.d("LOGIN/USER",user.toString())
                when (val code = loginResponse.code) {
                    1000 -> {
                        loginView.onLoginSuccess(code, loginResponse.result!!)
                        Log.d("LOGIN/SUCCESS", response.toString())
                    }
                    else -> {
                        loginView.onLoginFailure()
                        Log.d("LOGIN/CODE", response.body().toString())
                    }
                }
            }


            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })
        Log.d("LOGIN", "HELLO")
    }

}
