package com.dumanyusuf.tabuchallenge.presentation.next_team_page

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
fun NextTeamPage(
    firstTeamScore: Int,
    gameSettings: String,
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
            // Skor gösterimi
            Box(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF8A72E0))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Birinci Takımın Skoru: $firstTeamScore",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "2. Takım Hazırsanız Başlayalım!",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // İkinci takım için takım listesini oluştur ve ilk takımın skorunu kaydet
                    val teamList = "[{\"teamName\":\"Takım 2\",\"score\":0,\"firstTeamScore\":$firstTeamScore}]"
                    
                    navController.navigate(Screan.GameScreanPage.route + "/" + gameSettings + "/" + teamList)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8A72E0)
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .height(56.dp)
            ) {
                Text(
                    text = "Başla",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}