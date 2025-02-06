package com.dumanyusuf.tabuchallenge.presentation.game_screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.tabuchallenge.Screan
import com.dumanyusuf.tabuchallenge.domain.model.GameSettings
import com.dumanyusuf.tabuchallenge.domain.model.TeamName
import com.dumanyusuf.tabuchallenge.domain.model.Words
import com.dumanyusuf.tabuchallenge.presentation.game_screan.GameViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@SuppressLint("ContextCastToActivity")
@Composable
fun GameScrean(
    viewModel: GameViewModel = hiltViewModel(),
    navController: NavController,
    gameSettings: String,
    teamList: String
) {
    val words= viewModel.wordsState.collectAsState().value
    val currentWordIndex = viewModel.currentWordIndex.collectAsState().value

    val gameSettings = remember { Gson().fromJson(gameSettings, GameSettings::class.java) }

    val teamListType = object : TypeToken<List<TeamName>>() {}.type
    val teamList: List<TeamName> = remember {
        Gson().fromJson(teamList, teamListType)
    }

    val activity = (LocalContext.current as Activity)

    val showExitDialog = remember { mutableStateOf(false) }
    val showHomeDialog = remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog.value = true
    }

    if (showExitDialog.value) {
        ExitConfirmationDialog(
            titleText = "Çıkış Onayı",
            messageText = "Uygulamadan çıkmak istediğinize emin misiniz?",
            confirmText = "Evet",
            dismissText = "Hayır",
            onConfirm = {
                activity.finish()
            },
            onDismiss = {
                showExitDialog.value = false
            }
        )
    }

    if (showHomeDialog.value) {
        ExitConfirmationDialog(
            titleText = "Ana Menü Onayı",
            messageText = "Ana menüye dönmek istediğinize emin misiniz?",
            confirmText = "Evet",
            dismissText = "Hayır",
            onConfirm = {
                showHomeDialog.value = false
                navController.navigate(Screan.WelcomePage.route)
            },
            onDismiss = {
                showHomeDialog.value = false
            }
        )
    }

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
        TopPanel(onHomeClick = {
            showHomeDialog.value = true
        })

        TeamNameCompose(teamList)

        // Timer & Words List
        WordAndTimerSection(gameSettings = gameSettings, words, currentWordIndex)

        // Buttons Section
        ActionButtonsRow(gameSettings, viewModel::onCorrect)
    }
}

@Composable
fun TeamNameCompose(teamList: List<TeamName>) {
    val team1 = teamList.getOrNull(0)
    val team2 = teamList.getOrNull(1)

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
            Column {

                Text(
                    text = team1?.teamName ?: "Takım 1",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = "skor:0",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 4.dp)
                )
            }

            // ayraç
            Text(
                text = "-",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Column {
                Text(
                    text = team2?.teamName ?: "Takım 2",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = "skor:0",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
        }
    }
}

@Composable
fun TopPanel(onHomeClick: () -> Unit) {
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
            Text(text = "\uD83C\uDFA7", fontSize = 24.sp, color = Color.White)
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
                .background(Color(0xFF6A57DB))
                .padding()
                .clickable { onHomeClick() },
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
fun WordAndTimerSection(gameSettings: GameSettings, words: List<Words>, currentWordIndex: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gameSettings.gameTime.toString(),
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
            if (words.isNotEmpty()) {
                val word = words[currentWordIndex]
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(word.mainWord, fontSize = 24.sp, color = Color.White)

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = word.forbiddenWords.joinToString("\n"),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Text(
                    text = "No words available",
                    color = Color.White,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ActionButtonsRow(gameSettings: GameSettings, onCorrect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        GameButton("TABUU", Color(0xFFD32F2F))
        GameButton("PAS(${gameSettings.passCount})", Color(0xFFFF9800))
        GameButton("DOĞRU", Color(0xFF388E3C), onCorrect)
    }
}

@Composable
fun GameButton(text: String, backgroundColor: Color, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor),
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .padding(4.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 16.sp)
    }
}

@Composable
fun ExitConfirmationDialog(
    titleText: String,
    messageText: String,
    confirmText: String,
    dismissText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        containerColor = Color(0xFF6A57DB),
        onDismissRequest = {},
        title = {
            Text(
                color = Color.White,
                text = titleText
            )
        },
        text = {
            Text(
                color = Color.White,
                text = messageText
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    fontSize = 24.sp,
                    color = Color.Yellow,
                    text = confirmText
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    fontSize = 24.sp,
                    color = Color.Red,
                    text = dismissText
                )
            }
        }
    )
}