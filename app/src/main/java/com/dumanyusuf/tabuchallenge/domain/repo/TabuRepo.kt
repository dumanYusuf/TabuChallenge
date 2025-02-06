package com.dumanyusuf.tabuchallenge.domain.repo

import com.dumanyusuf.tabuchallenge.domain.model.GameSettings
import com.dumanyusuf.tabuchallenge.domain.model.TeamName



interface TabuRepo{
    suspend fun addTeamFirebase(team1:String,team2:String):List<TeamName>
    suspend fun addGameSettings(time:Int,passCount:Int,roundCount:Int):GameSettings
}