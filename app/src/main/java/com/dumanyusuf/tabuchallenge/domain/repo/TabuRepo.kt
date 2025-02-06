package com.dumanyusuf.tabuchallenge.domain.repo

import com.dumanyusuf.tabuchallenge.domain.model.GameSettings
import com.dumanyusuf.tabuchallenge.domain.model.TeamName
import com.dumanyusuf.tabuchallenge.domain.model.Words


interface TabuRepo{
    suspend fun addTeamFirebase(team1:String,team2:String):List<TeamName>
    suspend fun addGameSettings(time:Int,passCount:Int,roundCount:Int):GameSettings
    suspend fun getWord():List<Words>

}