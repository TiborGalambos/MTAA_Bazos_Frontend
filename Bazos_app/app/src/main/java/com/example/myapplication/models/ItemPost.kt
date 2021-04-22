package com.example.myapplication.models

import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.io.File

data class ItemPost (
    @SerializedName("category")
    var category: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("content")
    var content: String,

    @SerializedName("price")
    var price: Int,

    @SerializedName("address")
    var address: String,

    @SerializedName("photo")
    var photo: File

)