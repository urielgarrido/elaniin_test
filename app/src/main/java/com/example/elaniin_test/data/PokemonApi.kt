package com.example.elaniin_test.data

import com.example.elaniin_test.data.model.RegionResponse
import retrofit2.Response
import retrofit2.http.GET

interface PokemonApi {

    @GET("region")
    suspend fun getAllRegions(): Response<RegionResponse>
}