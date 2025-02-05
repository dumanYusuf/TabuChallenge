package com.dumanyusuf.tabuchallenge.data.repo

import com.dumanyusuf.tabuchallenge.domain.model.TeamName
import com.dumanyusuf.tabuchallenge.domain.repo.TabuRepo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class TabuImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TabuRepo {

    override suspend fun addTeamFirebase(team1: String, team2: String) {

        // Mevcut takımları temizle
        firestore.collection("teams").get().await().documents.forEach {
            it.reference.delete().await()
        }

        val team1Id = UUID.randomUUID().toString()
        val team2Id = UUID.randomUUID().toString()

        val team1Data = TeamName(id = team1Id, teamName = team1, score = 0)
        val team2Data = TeamName(id = team2Id, teamName = team2, score = 0)

        firestore.collection("teams")
            .document(team1Id)
            .set(team1Data.toMap())
            .await()

        firestore.collection("teams")
            .document(team2Id)
            .set(team2Data.toMap())
            .await()
    }

    override suspend fun getTeamList():Flow< List<TeamName>>  = callbackFlow{
        val listenerRegistration = firestore.collection("teams").addSnapshotListener { snapshot, e ->
            if (e != null) {
                close(e)
                return@addSnapshotListener
            }

            val teamList = mutableListOf<TeamName>()
            if (snapshot != null) {
                for (document in snapshot.documents) {
                    val team = document.toObject(TeamName::class.java)
                    if (team != null) {
                        teamList.add(team)
                    }
                }
            }
            trySend(teamList).isSuccess
        }
        awaitClose { listenerRegistration.remove() }
    }


}
