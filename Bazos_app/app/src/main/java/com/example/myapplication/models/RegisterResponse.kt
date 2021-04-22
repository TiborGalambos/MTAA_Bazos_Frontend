package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class RegisterResponse (

    @SerializedName("user")
    var user: User,

    @SerializedName("token")
    var token: String

    )