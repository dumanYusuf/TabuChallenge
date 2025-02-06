package com.dumanyusuf.tabuchallenge.domain.use_case.team_name

import com.dumanyusuf.tabuchallenge.domain.model.GameSettings
import com.dumanyusuf.tabuchallenge.domain.model.TeamName
import com.dumanyusuf.tabuchallenge.domain.repo.TabuRepo
import javax.inject.Inject

class TeamNameUseCase @Inject constructor(private val repo: TabuRepo){


   suspend fun addTeam(team1:String,team2:String):List<TeamName>{
        return repo.addTeamFirebase(team1, team2)
    }

    suspend fun addSettings(time: Int, passCount: Int, roundCount: Int): GameSettings {
        return repo.addGameSettings(time, passCount, roundCount)
    }

}