package com.example.elaniin_test.teams.my_teams.domain

import com.example.elaniin_test.teams.my_teams.data.TeamsRepository
import com.example.elaniin_test.teams.my_teams.model.Team
import javax.inject.Inject

class DeleteTeamUseCase @Inject constructor(private val teamsRepository: TeamsRepository) {

    operator fun invoke(teamToDelete: Team): Boolean {
        return teamsRepository.deleteTeam(teamToDelete)
    }
}