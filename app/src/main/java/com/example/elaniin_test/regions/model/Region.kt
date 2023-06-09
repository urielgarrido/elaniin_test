package com.example.elaniin_test.regions.model

import com.google.gson.annotations.SerializedName

data class Region(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
) {
    fun getId(): Int {
        val regex = Regex("/(\\d+)/$")
        val matchResult = regex.find(url)

        return matchResult?.groupValues?.get(1)?.toInt() ?: -1
    }
}
