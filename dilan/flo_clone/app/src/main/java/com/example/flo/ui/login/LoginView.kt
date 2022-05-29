package com.example.flo.ui.login

import com.example.flo.data.remote.auth.Result

interface LoginView {
    fun onLoginSuccess(code: Int, result: Result)
    fun onLoginFailure()
}