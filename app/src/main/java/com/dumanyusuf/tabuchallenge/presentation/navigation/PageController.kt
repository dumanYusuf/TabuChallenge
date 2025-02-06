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
import com.dumanyusuf.tabuchallenge.presentation.welcome_page.WelcomePage


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
        /*composable(Screan.StartingPage.route) {
            StartingPage(navcontroller)
        }*/
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
    }

}