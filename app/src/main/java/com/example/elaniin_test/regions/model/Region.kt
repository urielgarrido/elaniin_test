package com.example.elaniin_test.regions.model

import com.google.gson.annotations.SerializedName

data class Region(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)
