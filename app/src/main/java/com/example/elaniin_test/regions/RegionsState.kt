package com.example.elaniin_test.regions

import com.example.elaniin_test.regions.model.Region

data class RegionsState(
    val regions: List<Region> = emptyList(),
    val errorMessage: Int? = null
)
