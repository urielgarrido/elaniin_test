package com.example.elaniin_test.data.model

import com.example.elaniin_test.regions.model.Region
import com.google.gson.annotations.SerializedName

data class RegionResponse(
    @SerializedName("results") val regionsResult: List<Region>
)
