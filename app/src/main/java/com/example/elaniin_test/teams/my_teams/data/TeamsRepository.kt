package com.example.elaniin_test.teams.my_teams.data

import com.example.elaniin_test.data.FirebaseDataSource
import com.example.elaniin_test.teams.my_teams.model.Team
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class TeamsRepository @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {

    fun getAllTeamsByRegion(valueEventListener: ValueEventListener, region: String) {
        return firebaseDataSource.getAllTeamsByRegion(valueEventListener, region)
    }

    suspend fun createTeam(newTeam: Team): Boolean {
        return firebaseDataSource.createTeam(newTeam)
    }

    suspend fun editTeam(teamToEdit: Team): Boolean {
        return firebaseDataSource.editTeam(teamToEdit)
    }

    fun deleteTeam(teamToDelete: Team): Boolean {
        return firebaseDataSource.deleteTeam(teamToDelete)
    }

    suspend fun copyTeamFromOtherUser(region: String, token: String) {
        return firebaseDataSource.copyTeamFromOtherUser(region, token)
    }


}