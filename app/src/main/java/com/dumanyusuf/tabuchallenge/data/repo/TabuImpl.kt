package com.dumanyusuf.tabuchallenge.data.repo

import android.util.Log
import com.dumanyusuf.tabuchallenge.domain.model.GameSettings
import com.dumanyusuf.tabuchallenge.domain.model.TeamName
import com.dumanyusuf.tabuchallenge.domain.model.Words
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

    override suspend fun addTeamFirebase(team1: String, team2: String) :List<TeamName>{

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
        return listOf(team1Data,team2Data)
    }

    override suspend fun addGameSettings(time: Int, passCount: Int, roundCount: Int): GameSettings {
        val gameSettings = GameSettings(
            settingId = UUID.randomUUID().toString(),
            gameTime = time,
            passCount = passCount,
            roundCount = roundCount
        )

        firestore.collection("settings")
            .document(gameSettings.settingId)
            .set(gameSettings.toMap())
            .await()

        return gameSettings
    }

    override suspend fun getWord(): List<Words> {
        return try {
            val snapshot = firestore.collection("words").get().await()
            snapshot.documents.mapNotNull { document ->
                val word = document.toObject(Words::class.java)
                if (word != null) {
                    Log.d("TabuImpl", "Fetched word: $word")
                } else {
                    Log.e("TabuImpl", "Failed to deserialize document: ${document.id}")
                }
                word
            }
        } catch (e: Exception) {
            Log.e("TabuImpl", "Error getting words1:${e.localizedMessage}")
            emptyList()
        }
    }

}
