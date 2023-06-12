package com.example.elaniin_test.data.model

import com.google.gson.annotations.SerializedName

data class RegionForIdResponse(
    @SerializedName("pokedexes") val pokedexes: List<Pokedex>
)

data class Pokedex(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
) {
    fun getId(): Int {
        val regex = Regex("/(\\d+)/$")
        val matchResult = regex.find(url)

        return matchResult?.groupValues?.get(1)?.toInt() ?: -1
    }
}
