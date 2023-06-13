package com.example.elaniin_test.regions.data

import com.example.elaniin_test.data.model.Pokedex
import com.example.elaniin_test.regions.model.Region
import javax.inject.Inject

class RegionsRepository @Inject constructor(
    private val regionsDataSource: RegionsDataSource
) {

    suspend fun getAllRegions(): List<Region>? {
        val result = regionsDataSource.getAllRegions()
        return result?.regionsResult
    }

    suspend fun getRegionById(regionId: Int): List<Pokedex>? {
        val result = regionsDataSource.getRegionId(regionId)
        return result?.pokedexes
    }
}