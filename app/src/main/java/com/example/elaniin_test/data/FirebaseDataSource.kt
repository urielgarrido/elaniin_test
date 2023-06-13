package com.example.elaniin_test.data

import com.example.elaniin_test.teams.my_teams.model.Team
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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
           database.child(userId).child(TEAMS_ROOT).child(newTeam.region).push().setValue(newTeam).await()
        }
       return database.child(TEAMS_ROOT).child(newTeam.shareToken).setValue(newTeam).isSuccessful
    }

    suspend fun editTeam() {

    }

    suspend fun deleteTeam() {

    }

    suspend fun copyTeamFromOtherUser(teamToCopy: Team) {
        val snapshot: DataSnapshot = database.child(TEAMS_ROOT).child(teamToCopy.shareToken).get().await()
        if (snapshot.exists()) {
            userId?.let { userId ->
                database.child(userId).child(TEAMS_ROOT).setValue(teamToCopy)
            }
        }
    }
}