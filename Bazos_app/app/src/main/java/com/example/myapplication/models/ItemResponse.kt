package com.example.myapplication.models

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class ItemResponse (

    @SerializedName("id")
    var id: Int,

    @SerializedName("author_name")
    var author_name: String,

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
    var photo: String,

    @SerializedName("author")
    var author: Int

)