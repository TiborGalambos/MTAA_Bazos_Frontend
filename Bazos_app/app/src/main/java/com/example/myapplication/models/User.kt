package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("id")
    var id: Int,

    @SerializedName("username")
    var username: String,

    @SerializedName("email")
    var email: String

)