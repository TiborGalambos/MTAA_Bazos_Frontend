package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class LoginResponse (

    @SerializedName("expiry")
    var expiry: String,

    @SerializedName("token")
    var token: String

    )