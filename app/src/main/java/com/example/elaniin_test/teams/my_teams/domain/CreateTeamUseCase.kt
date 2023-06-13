package com.example.elaniin_test.teams.my_teams.domain

import com.example.elaniin_test.teams.my_teams.data.TeamsRepository
import com.example.elaniin_test.teams.my_teams.model.Team
import javax.inject.Inject

class CreateTeamUseCase @Inject constructor(private val teamsRepository: TeamsRepository) {

    suspend operator fun invoke(newTeam: Team): Boolean {
        return teamsRepository.createTeam(newTeam)
    }
}