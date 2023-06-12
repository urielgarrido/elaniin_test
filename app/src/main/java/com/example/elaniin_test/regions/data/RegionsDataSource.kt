package com.example.elaniin_test.regions.data

import com.example.elaniin_test.data.PokemonApi
import com.example.elaniin_test.data.model.AllRegionResponse
import com.example.elaniin_test.data.model.RegionForIdResponse
import javax.inject.Inject

class RegionsDataSource @Inject constructor(
    private val pokemonApi: PokemonApi
) {

    suspend fun getAllRegions(): AllRegionResponse? {
        return pokemonApi.getAllRegions().body()
    }

    suspend fun getRegionId(regionId: Int): RegionForIdResponse? {
        return pokemonApi.getRegionId(regionId).body()
    }


}