package com.dumanyusuf.tabuchallenge.presentation.game_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.dumanyusuf.tabuchallenge.domain.model.TeamName

@Composable
fun GameScrean() {
    Column(
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
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Row
        TopPanel()

        TeamNameCompose()

        // Timer & Words List
        WordAndTimerSection()

        // Buttons Section
        ActionButtonsRow()
    }
}


@Composable
fun TeamNameCompose() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF8A72E0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Birinci Takım ve Skor
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "😊 | 1T ",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = "0",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }

            // Skor Ayracı ve İkinci Takım Skoru
            Text(
                text = "-",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "0 ",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = "2T | 😊",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}


@Composable
fun TopPanel() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Sound Icon Placeholder
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF6A57DB)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "\uD83C\uDFA7", fontSize = 24.sp, color = Color.White) // 🎧 için Unicode
        }


        // Pause Button
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF8A72E0))
                .padding(horizontal = 24.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Oyunu Durdur", color = Color.White, fontSize = 16.sp)
        }

        // Home Icon Placeholder
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF6A57DB)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                tint = Color.White
            )
        }
    }
}

@Composable
fun WordAndTimerSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "42",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(16.dp))
                .height(200.dp)
                .background(Color(0xFF8A72E0))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("FABL", fontSize = 24.sp, color = Color.White)

                Spacer(modifier = Modifier.height(8.dp))
                Text("Insan\nMasal\nLa Fontaine\nHayvan\nHikaye", color = Color.White, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun ActionButtonsRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        GameButton("TABUU", Color(0xFFD32F2F))
        GameButton("PAS(3)", Color(0xFFFF9800))
        GameButton("DOĞRU", Color(0xFF388E3C))
    }
}

@Composable
fun GameButton(label: String, backgroundColor: Color) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor),
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .padding(4.dp)
    ) {
        Text(text = label, color = Color.White, fontSize = 16.sp)
    }
}
