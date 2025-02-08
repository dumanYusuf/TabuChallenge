package com.dumanyusuf.tabuchallenge

sealed class Screan(val  route:String){
    object WelcomePage:Screan("welcome")
    object HowToPage:Screan("how_to_page")
    object TeamNamePage:Screan("team_name_page")
    object GameScreanPage:Screan("game_screan")
    object NextTeamPage:Screan("next_team_page")
    object WinTeamPage:Screan("win_team_page")
}
