package com.dumanyusuf.tabuchallenge

sealed class Screan(val  route:String){
    object WelcomePage:Screan("welcome")
    object HowToPage:Screan("how_to_page")
    object TeamNamePage:Screan("team_name_page")
   // object StartingPage:Screan("starting_page")
    object GameScreanPage:Screan("game_screan")
}
