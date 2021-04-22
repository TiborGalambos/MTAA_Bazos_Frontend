package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class DeleteResponse(
        @SerializedName("message")
        var message: String,
)