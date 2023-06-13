package com.example.elaniin_test.teams.my_teams.domain

import com.example.elaniin_test.teams.my_teams.data.TeamsRepository
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class GetAllTeamsByRegionUseCase @Inject constructor(private val teamsRepository: TeamsRepository) {

    operator fun invoke(valueEventListener: ValueEventListener, region: String) {
        return teamsRepository.getAllTeamsByRegion(valueEventListener, region)
    }
}