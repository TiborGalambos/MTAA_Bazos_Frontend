package com.example.myapplication.models

import com.example.myapplication.activityLogic.Items
import com.google.gson.annotations.SerializedName

data class ItemList (

    @SerializedName("items")
    var items: List<ItemResponse>

)