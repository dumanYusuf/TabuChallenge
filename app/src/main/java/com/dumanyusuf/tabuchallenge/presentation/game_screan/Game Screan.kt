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
import androidx.compose.runtime.*
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val words = viewModel.wordsState.collectAsState().value
    val currentWordIndex = viewModel.currentWordIndex.collectAsState().value
    val currentScore = viewModel.score.collectAsState().value
    val passCount = viewModel.passCount.collectAsState().value
    val time = viewModel.time.collectAsState().value

    val gameSettingsObj = remember { Gson().fromJson(gameSettings, GameSettings::class.java) }

    LaunchedEffect(gameSettingsObj) {
        viewModel.setPassCount(gameSettingsObj.roundCount)
        // İkinci takım için firstTeamScore'u kontrol et
        val teamListObj = Gson().fromJson<List<TeamName>>(teamList, object : TypeToken<List<TeamName>>() {}.type)
        val firstTeamScore = teamListObj.firstOrNull()?.firstTeamScore ?: 0
        viewModel.timerGame(gameSettingsObj, firstTeamScore != 0, firstTeamScore)
    }

    val teamListType = object : TypeToken<List<TeamName>>() {}.type
    val teamList: List<TeamName> = remember {
        Gson().fromJson(teamList, teamListType)
    }

    // Süre bittiğinde yönlendirme yap
    val navigateToNextTeam = viewModel.navigateToNextTeam.collectAsStateWithLifecycle().value
    val navigateToWinTeam = viewModel.navigateToWinTeam.collectAsStateWithLifecycle().value

    LaunchedEffect(navigateToNextTeam) {
        if (navigateToNextTeam) {
            // İlk takımın skorunu ve game settings'i NextTeamPage'e gönder
            val currentScore = viewModel.getCurrentScore()
            navController.navigate(Screan.NextTeamPage.createRoute(currentScore, gameSettings))
        }
    }

    LaunchedEffect(navigateToWinTeam) {
        if (navigateToWinTeam) {
            // Kazanan takımı belirle ve WinTeamPage'e yönlendir
            val winningTeam = viewModel.getWinningTeam()
            navController.navigate(Screan.WinTeamPage.createRoute(winningTeam))
        }
    }

    val activity = (LocalContext.current as Activity)

    val showExitDialog = remember { mutableStateOf(false) }
    val showHomeDialog = remember { mutableStateOf(false) }
    val showPauseDialog = remember { mutableStateOf(false) }

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

    if (showPauseDialog.value) {
        AlertDialog(
            containerColor = Color(0xFF6A57DB),
            onDismissRequest = { },
            title = {
                Text(
                    text = "Oyun Duraklatıldı",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Ne yapmak istersiniz?",
                    color = Color.White,
                    fontSize = 16.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPauseDialog.value = false
                        viewModel.resumeGame()
                    }
                ) {
                    Text(
                        text = "Devam Et",
                        color = Color.Yellow,
                        fontSize = 18.sp
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showPauseDialog.value = false
                        navController.navigate(Screan.WelcomePage.route)
                    }
                ) {
                    Text(
                        text = "Oyundan Çık",
                        color = Color.Red,
                        fontSize = 18.sp
                    )
                }
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { showHomeDialog.value = true },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF6A57DB))
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White
                )
            }

            // Durdur butonu
            Button(
                onClick = {
                    viewModel.pauseGame()
                    showPauseDialog.value = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A57DB)
                ),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(48.dp)
            ) {
                Text("Durdur", color = Color.White)
            }
        }

        TeamNameCompose(teamList[0], currentScore)

        // Timer & Words List
        WordAndTimerSection(gameSettings = gameSettingsObj, words, currentWordIndex,time)

        // Buttons Section
        ActionButtonsRow(viewModel::onCorrect, viewModel::onTabu, viewModel::onPass, passCount)
    }
}

@Composable
fun TeamNameCompose(team: TeamName, score: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF8A72E0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = team.teamName,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Text(
                text = score.toString(),
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun WordAndTimerSection(gameSettings: GameSettings, words: List<Words>, currentWordIndex: Int,gameTime:Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gameTime.toString(),
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
                CircularProgressIndicator(
                    modifier = Modifier.padding(),
                    color = Color.Red
                )
            }
        }
    }
}

@Composable
fun ActionButtonsRow(onCorrect: () -> Unit, onTabu: () -> Unit, onPass: () -> Unit, passCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        GameButton("TABUU", Color(0xFFD32F2F), onTabu)
        GameButton("PAS(${passCount})", Color(0xFFFF9800), onPass, passCount > 0)
        GameButton("DOĞRU", Color(0xFF388E3C), onCorrect)
    }
}

@Composable
fun GameButton(text: String, backgroundColor: Color, onClick: () -> Unit = {}, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor),
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .padding(4.dp),
        enabled = enabled
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
            TextButton(onClick = onDismiss)
            { Text( fontSize = 24.sp, color = Color.Red, text = dismissText )
            }
        }
    )
}