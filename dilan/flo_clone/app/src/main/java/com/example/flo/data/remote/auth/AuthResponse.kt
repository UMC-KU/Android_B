package com.example.flo.data.remote.auth

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName(value = "result") val result: Result?
)

data class Result(
    @SerializedName(value = "userIdx") var userIdx: Int,
    @SerializedName(value = "jwt") var jwt: String
)


