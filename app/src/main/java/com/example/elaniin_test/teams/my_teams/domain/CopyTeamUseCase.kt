package com.example.elaniin_test.teams.my_teams.domain

import com.example.elaniin_test.teams.my_teams.data.TeamsRepository
import javax.inject.Inject


class CopyTeamUseCase @Inject constructor(private val teamsRepository: TeamsRepository) {

    suspend operator fun invoke(region: String, token: String){
        return teamsRepository.copyTeamFromOtherUser(region, token)
    }
}