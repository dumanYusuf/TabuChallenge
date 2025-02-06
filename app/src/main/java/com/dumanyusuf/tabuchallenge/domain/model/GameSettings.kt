package com.dumanyusuf.tabuchallenge.domain.model

data class GameSettings(
    val settingId:String="",
    var gameTime:Int,
    var passCount:Int,
    var roundCount:Int
){
    fun toMap():Map<String,Any>{
        return mapOf(
            "settingId" to settingId,
            "gameTime" to gameTime,
            "passCount" to passCount,
            "roundCount" to roundCount
        )
    }
}
