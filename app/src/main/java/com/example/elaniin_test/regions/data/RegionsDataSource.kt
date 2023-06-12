package com.example.elaniin_test.regions.data

import com.example.elaniin_test.data.PokemonApi
import com.example.elaniin_test.data.model.RegionResponse
import javax.inject.Inject

class RegionsDataSource @Inject constructor(
    private val pokemonApi: PokemonApi
) {

    suspend fun getAllRegions(): RegionResponse? {
        return pokemonApi.getAllRegions().body()
    }


}