package com.dumanyusuf.tabuchallenge.domain.repo

import com.dumanyusuf.tabuchallenge.domain.model.GameSettings
import com.dumanyusuf.tabuchallenge.domain.model.TeamName
import kotlinx.coroutines.flow.Flow


interface TabuRepo{
    suspend fun addTeamFirebase(team1:String,team2:String)
    suspend fun getTeamList():Flow<List<TeamName>>
    suspend fun addGameSettings(time:Int,passCount:Int,roundCount:Int):GameSettings
}