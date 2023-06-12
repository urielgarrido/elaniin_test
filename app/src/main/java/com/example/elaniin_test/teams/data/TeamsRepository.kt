package com.example.elaniin_test.teams.data

import com.example.elaniin_test.data.FirebaseDataSource
import com.example.elaniin_test.teams.model.Team
import javax.inject.Inject

class TeamsRepository @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {

    suspend fun createTeam(newTeam: Team) {
        firebaseDataSource.createTeam(newTeam)
    }

    suspend fun editTeam() {
        firebaseDataSource.editTeam()
    }

    suspend fun deleteTeam() {
        firebaseDataSource.deleteTeam()
    }

    suspend fun copyTeamFromOtherUser(teamToCopy: Team) {
        firebaseDataSource.copyTeamFromOtherUser(teamToCopy)
    }


}