package com.dumanyusuf.tabuchallenge.presentation.navigation

import TeamNamePage
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dumanyusuf.tabuchallenge.Screan
import com.dumanyusuf.tabuchallenge.presentation.game_screen.GameScrean
import com.dumanyusuf.tabuchallenge.presentation.how_to_page.HowToPage
import com.dumanyusuf.tabuchallenge.presentation.next_team_page.NextTeamPage
import com.dumanyusuf.tabuchallenge.presentation.welcome_page.WelcomePage
import com.dumanyusuf.tabuchallenge.presentation.win_team_page.WinTeamPage
import com.dumanyusuf.tabuchallenge.presentation.win_team_page.TeamScore


@Composable
fun PageController() {

    val navcontroller=rememberNavController()

    NavHost(navController = navcontroller, startDestination = Screan.WelcomePage.route){
        composable(Screan.WelcomePage.route) {
            WelcomePage(navcontroller)
        }
        composable(Screan.HowToPage.route) {
            HowToPage(navController = navcontroller)
        }
        composable(Screan.TeamNamePage.route) {
            TeamNamePage(navController = navcontroller, onBackPress = {
                navcontroller.popBackStack()
            })
        }

        composable(
            Screan.GameScreanPage.route + "/{gameSettingsJson}/{teamListJson}",
            arguments = listOf(
                navArgument("gameSettingsJson") { type = NavType.StringType },
                navArgument("teamListJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val gameSettingsJson = backStackEntry.arguments?.getString("gameSettingsJson")
            val teamListJson = backStackEntry.arguments?.getString("teamListJson")!!
            GameScrean(
                navController = navcontroller,
                gameSettings = gameSettingsJson!!,
                teamList = teamListJson)
        }
        composable(
            route = Screan.NextTeamPage.route,
            arguments = listOf(
                navArgument("firstTeamScore") { type = NavType.IntType },
                navArgument("gameSettings") { type = NavType.StringType },
                navArgument("teamList") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val firstTeamScore = backStackEntry.arguments?.getInt("firstTeamScore") ?: 0
            val gameSettings = backStackEntry.arguments?.getString("gameSettings") ?: ""
            val teamList = backStackEntry.arguments?.getString("teamList") ?: ""
            NextTeamPage(
                firstTeamScore = firstTeamScore,
                gameSettings = gameSettings,
                teamList = teamList,
                navController = navcontroller
            )
        }

        composable(
            route = Screan.WinTeamPage.route,
            arguments = listOf(
                navArgument("winningTeam") { type = NavType.StringType },
                navArgument("team1Name") { type = NavType.StringType },
                navArgument("team1Score") { type = NavType.IntType },
                navArgument("team2Name") { type = NavType.StringType },
                navArgument("team2Score") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val winningTeam = backStackEntry.arguments?.getString("winningTeam") ?: ""
            val team1Name = backStackEntry.arguments?.getString("team1Name") ?: ""
            val team1Score = backStackEntry.arguments?.getInt("team1Score") ?: 0
            val team2Name = backStackEntry.arguments?.getString("team2Name") ?: ""
            val team2Score = backStackEntry.arguments?.getInt("team2Score") ?: 0
            
            WinTeamPage(
                winningTeam = winningTeam,
                team1Score = TeamScore(team1Name, team1Score),
                team2Score = TeamScore(team2Name, team2Score),
                navController = navcontroller
            )
        }
    }

}