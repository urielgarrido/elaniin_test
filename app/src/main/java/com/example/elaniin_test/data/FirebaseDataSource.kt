package com.example.elaniin_test.data

import com.example.elaniin_test.teams.my_teams.model.Team
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataSource @Inject constructor() {

    private val database = Firebase.database.reference
    private val userId = Firebase.auth.currentUser?.uid

    companion object {
        const val TEAMS_ROOT = "teams"
    }

    fun getAllTeamsByRegion(valueEventListener: ValueEventListener, region: String) {
        userId?.let { userId ->
            database.child(userId).child(TEAMS_ROOT).child(region).addValueEventListener(valueEventListener)
        }
    }

    suspend fun createTeam(newTeam: Team): Boolean {
        userId?.let { userId ->
            database.child(userId).child(TEAMS_ROOT).child(newTeam.region).child(newTeam.shareToken).setValue(newTeam).await()
        }
        return database.child(TEAMS_ROOT).child(newTeam.region).child(newTeam.shareToken).setValue(newTeam).isSuccessful
    }

    suspend fun editTeam(teamToEdit: Team): Boolean {
        userId?.let { userId ->
            database.child(userId).child(TEAMS_ROOT).child(teamToEdit.region).child(teamToEdit.shareToken).setValue(teamToEdit)
                .await()
        }
        return database.child(TEAMS_ROOT).child(teamToEdit.region).child(teamToEdit.shareToken).setValue(teamToEdit).isSuccessful
    }

    fun deleteTeam(teamToDelete: Team): Boolean {
        userId?.let { userId ->
            return database.child(userId).child(TEAMS_ROOT).child(teamToDelete.region).child(teamToDelete.shareToken)
                .removeValue()
                .isSuccessful
        } ?: return false
    }

    suspend fun copyTeamFromOtherUser(region: String, token: String) {
        val snapshot: DataSnapshot =
            database.child(TEAMS_ROOT).child(region).child(token).get().await()
        val teamToCopy = snapshot.getValue<Team>()
        if (snapshot.exists()) {
            userId?.let { userId ->
                database.child(userId).child(TEAMS_ROOT).child(region).child(token)
                    .setValue(teamToCopy).await()
            }
        }
    }
}