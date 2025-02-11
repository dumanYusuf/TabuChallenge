package com.dumanyusuf.tabuchallenge.presentation.win_team_page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dumanyusuf.tabuchallenge.Screan

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WinTeamPage(
    winningTeam: String,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF5E4FDB),
                        Color(0xFF312A67)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF8A72E0))
                    .padding(32.dp)
            ) {
                Text(
                    text = when (winningTeam) {
                        "Berabere" -> "Oyun Berabere Bitti!"
                        else -> "Kazanan: $winningTeam!"
                    },
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    navController.navigate(Screan.WelcomePage.route) {
                        popUpTo(Screan.WelcomePage.route) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8A72E0)
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .height(56.dp)
            ) {
                Text(
                    text = "Ana Menüye Dön",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}