package com.dumanyusuf.tabuchallenge.presentation.welcome_page

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.tabuchallenge.R
import com.dumanyusuf.tabuchallenge.Screan
import com.dumanyusuf.tabuchallenge.presentation.how_to_page.HowToPage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WelcomePage(
    navcontroller:NavController
) {

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.tabuchat1),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.5f)
                                )
                            )
                        )
                )
            }


            val viewModel: WelcomeViewModel = hiltViewModel()
            val showSoundDialog = viewModel.showSoundDialog.collectAsState().value
            val showSettingsDialog = viewModel.showSettingsDialog.collectAsState().value
            val soundEnabled = viewModel.soundEnabled.collectAsState().value
            val musicEnabled = viewModel.musicEnabled.collectAsState().value
            val soundVolume = viewModel.soundVolume.collectAsState().value
            val isDarkTheme = viewModel.isDarkTheme.collectAsState().value
            val language = viewModel.language.collectAsState().value
            val vibrationEnabled = viewModel.vibrationEnabled.collectAsState().value
            val difficulty = viewModel.difficulty.collectAsState().value

            if (showSoundDialog) {
                AlertDialog(
                    containerColor = Color(0xFF6A57DB),
                    onDismissRequest = { viewModel.toggleSoundDialog() },
                    title = { 
                        Text(
                            text = "Ses Ayarları",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ) 
                    },
                    text = {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Oyun Sesleri", color = Color.White)
                                Switch(
                                    checked = soundEnabled,
                                    onCheckedChange = { viewModel.toggleSound() },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.Yellow,
                                        checkedTrackColor = Color.White
                                    )
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Müzik", color = Color.White)
                                Switch(
                                    checked = musicEnabled,
                                    onCheckedChange = { viewModel.toggleMusic() },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.Yellow,
                                        checkedTrackColor = Color.White
                                    )
                                )
                            }
                            Text("Ses Seviyesi", color = Color.White, modifier = Modifier.padding(top = 8.dp))
                            Slider(
                                value = soundVolume.toFloat(),
                                onValueChange = { viewModel.setSoundVolume(it.toInt()) },
                                valueRange = 0f..100f,
                                colors = SliderDefaults.colors(
                                    thumbColor = Color.Yellow,
                                    activeTrackColor = Color.White
                                )
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { viewModel.toggleSoundDialog() }) {
                            Text("Tamam", color = Color.Yellow)
                        }
                    }
                )
            }

            if (showSettingsDialog) {
                AlertDialog(
                    containerColor = Color(0xFF6A57DB),
                    onDismissRequest = { viewModel.toggleSettingsDialog() },
                    title = { 
                        Text(
                            text = "Ayarlar",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ) 
                    },
                    text = {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Koyu Tema", color = Color.White)
                                Switch(
                                    checked = isDarkTheme,
                                    onCheckedChange = { viewModel.toggleTheme() },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.Yellow,
                                        checkedTrackColor = Color.White
                                    )
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Titreşim", color = Color.White)
                                Switch(
                                    checked = vibrationEnabled,
                                    onCheckedChange = { viewModel.toggleVibration() },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.Yellow,
                                        checkedTrackColor = Color.White
                                    )
                                )
                            }
                            Text("Dil", color = Color.White, modifier = Modifier.padding(top = 8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                TextButton(
                                    onClick = { viewModel.setLanguage("tr") },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = if (language == "tr") Color.Yellow else Color.White
                                    )
                                ) {
                                    Text("Türkçe")
                                }
                                TextButton(
                                    onClick = { viewModel.setLanguage("en") },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = if (language == "en") Color.Yellow else Color.White
                                    )
                                ) {
                                    Text("English")
                                }
                            }
                            Text("Zorluk", color = Color.White, modifier = Modifier.padding(top = 8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                TextButton(
                                    onClick = { viewModel.setDifficulty("easy") },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = if (difficulty == "easy") Color.Yellow else Color.White
                                    )
                                ) {
                                    Text("Kolay")
                                }
                                TextButton(
                                    onClick = { viewModel.setDifficulty("normal") },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = if (difficulty == "normal") Color.Yellow else Color.White
                                    )
                                ) {
                                    Text("Normal")
                                }
                                TextButton(
                                    onClick = { viewModel.setDifficulty("hard") },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = if (difficulty == "hard") Color.Yellow else Color.White
                                    )
                                ) {
                                    Text("Zor")
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { viewModel.toggleSettingsDialog() }) {
                            Text("Tamam", color = Color.Yellow)
                        }
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.toggleSoundDialog() },
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.White.copy(alpha = 0.8f), CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.sound),
                        contentDescription = "Sound Toggle",
                        modifier = Modifier.size(50.dp),
                        tint = if (soundEnabled) Color.Black else Color.Gray
                    )
                }

                IconButton(
                    onClick = { viewModel.toggleSettingsDialog() },
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.White.copy(alpha = 0.8f), CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.setting),
                        contentDescription = "Settings",
                        modifier = Modifier.size(30.dp),
                        tint = Color.Black
                    )
                }
            }

            // Bottom Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    color = Color.White,
                    text = "Tabu'ya Hoşgeldin!",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Start Game Button
                Button(
                    onClick = {
                        navcontroller.navigate(Screan.TeamNamePage.route)
                    },
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(8.dp, shape = RoundedCornerShape(28.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE)
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = "OYUNA BAŞLA",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = Color.White
                    )
                }

                // How to Play Button
                Button(
                    onClick = {
                        navcontroller.navigate(Screan.HowToPage.route)
                    },
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(8.dp, shape = RoundedCornerShape(28.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF03DAC5)
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                        Text(
                            text = "Nasıl Oynanır?",
                            style = TextStyle(fontSize = 20.sp),
                            color = Color.White
                        )

                }
            }
        }
    }
}
