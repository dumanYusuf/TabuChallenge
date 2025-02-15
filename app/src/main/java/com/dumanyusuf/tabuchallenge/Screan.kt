package com.dumanyusuf.tabuchallenge

sealed class Screan(val route: String) {
    object WelcomePage : Screan("welcome")
    object HowToPage : Screan("how_to_page")
    object TeamNamePage : Screan("team_name_page")
    object GameScreanPage : Screan("game_screan")
    object NextTeamPage : Screan("next_team_page/{firstTeamScore}/{gameSettings}/{teamList}") {
        fun createRoute(firstTeamScore: Int, gameSettings: String, teamList: String) = "next_team_page/$firstTeamScore/$gameSettings/$teamList"
    }
    object WinTeamPage : Screan("win_team_page/{winningTeam}/{team1Name}/{team1Score}/{team2Name}/{team2Score}") {
        fun createRoute(
            winningTeam: String,
            team1Name: String,
            team1Score: Int,
            team2Name: String,
            team2Score: Int
        ) = "win_team_page/$winningTeam/$team1Name/$team1Score/$team2Name/$team2Score"
    }
}
