package com.dumanyusuf.tabuchallenge.domain.repo



interface TabuRepo{


    suspend fun addTeamFirebase(team1:String,team2:String)

}