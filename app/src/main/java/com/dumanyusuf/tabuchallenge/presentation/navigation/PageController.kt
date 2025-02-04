package com.dumanyusuf.tabuchallenge.presentation.navigation

import TeamNamePage
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dumanyusuf.tabuchallenge.Screan
import com.dumanyusuf.tabuchallenge.presentation.how_to_page.HowToPage
import com.dumanyusuf.tabuchallenge.presentation.starting.StartingPage
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
        composable(Screan.StartingPage.route) {
            StartingPage()
        }
    }

}