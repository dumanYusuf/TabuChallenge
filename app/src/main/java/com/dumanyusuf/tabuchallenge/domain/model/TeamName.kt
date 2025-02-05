package com.dumanyusuf.tabuchallenge.domain.model

data class TeamName(
    val id:String="",
    val teamName:String="",
    var score:Int=0,
    var tabuCount:Int=0,
    var correctCount:Int=0,
    var passLeft:Int=3
){
    fun toMap():Map<String,Any>{
        return mapOf(
            "id" to id,
            "teamName" to teamName,
            "score" to score,
            "tabuCount" to tabuCount,
            "correctCount" to correctCount,
            "passLeft" to passLeft
        )
    }
}
