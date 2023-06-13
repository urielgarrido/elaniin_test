package com.example.elaniin_test.regions.domain

import com.example.elaniin_test.data.model.Pokedex
import com.example.elaniin_test.regions.data.RegionsRepository
import javax.inject.Inject

class GetRegionByIdUseCase @Inject constructor(private val regionsRepository: RegionsRepository) {

    suspend operator fun invoke(regionId: Int): List<Pokedex>? {
        return regionsRepository.getRegionById(regionId)
    }
}