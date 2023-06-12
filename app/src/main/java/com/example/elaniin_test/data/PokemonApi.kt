package com.example.elaniin_test.data

import com.example.elaniin_test.data.model.AllRegionResponse
import com.example.elaniin_test.data.model.RegionForIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("region")
    suspend fun getAllRegions(): Response<AllRegionResponse>

    @GET("region/{id}")
    suspend fun getRegionId(@Path("id") regionId: Int): Response<RegionForIdResponse>
}