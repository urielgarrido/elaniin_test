package com.example.elaniin_test.teams.my_teams.domain

import com.example.elaniin_test.teams.my_teams.data.TeamsRepository
import com.example.elaniin_test.teams.my_teams.model.Team
import javax.inject.Inject

class EditTeamUseCase @Inject constructor(private val teamsRepository: TeamsRepository) {

    suspend operator fun invoke(teamToEdit: Team): Boolean {
        return teamsRepository.editTeam(teamToEdit)
    }
}