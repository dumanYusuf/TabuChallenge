package com.dumanyusuf.tabuchallenge.domain.use_case.game_screan

import com.dumanyusuf.tabuchallenge.domain.model.Words
import com.dumanyusuf.tabuchallenge.domain.repo.TabuRepo
import javax.inject.Inject

class GameScreanUseCase @Inject constructor(private val repo: TabuRepo) {

    suspend fun getWords():List<Words>{
        return repo.getWord()
    }

    suspend fun updateTeamScore(teamId: String, score: Int) {
        repo.updateTeamScore(teamId, score)
    }
}