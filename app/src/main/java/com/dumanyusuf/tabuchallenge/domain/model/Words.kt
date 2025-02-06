package com.dumanyusuf.tabuchallenge.domain.model

data class Words(
    val wordId:String="",
    val mainWord:String="",
    val forbiddenWords:List<String> = listOf(),
    val used:Boolean=false
)
