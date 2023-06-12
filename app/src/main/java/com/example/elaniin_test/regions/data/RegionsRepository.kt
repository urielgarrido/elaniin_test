package com.example.elaniin_test.regions.data

import com.example.elaniin_test.regions.model.Region
import javax.inject.Inject

class RegionsRepository @Inject constructor(
    private val regionsDataSource: RegionsDataSource
) {

    suspend fun getAllRegions(): List<Region>? {
        val result = regionsDataSource.getAllRegions()
        return result?.regionsResult
    }


}