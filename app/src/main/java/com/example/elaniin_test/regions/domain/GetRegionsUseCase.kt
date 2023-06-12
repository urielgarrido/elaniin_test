package com.example.elaniin_test.regions.domain

import com.example.elaniin_test.regions.model.Region
import javax.inject.Inject

class GetRegionsUseCase @Inject constructor() {
    operator fun invoke(): List<Region> {
        return listOf(
            Region("Name1"),
            Region("Name2"),
            Region("Name3"),
            Region("Name4"),
            Region("Name5"),
            Region("Name6"),
            Region("Name7"),
            Region("Name8"),
            Region("Name9"),
            Region("Name10"),
        )
    }
}