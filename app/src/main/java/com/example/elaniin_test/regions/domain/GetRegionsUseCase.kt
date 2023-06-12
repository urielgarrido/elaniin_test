package com.example.elaniin_test.regions.domain

import com.example.elaniin_test.regions.data.RegionsRepository
import com.example.elaniin_test.regions.model.Region
import javax.inject.Inject

class GetRegionsUseCase @Inject constructor(private val regionsRepository: RegionsRepository) {
    suspend operator fun invoke(): List<Region>? {
        return regionsRepository.getAllRegions()
    }
}