import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.tabuchallenge.R
import com.dumanyusuf.tabuchallenge.Screan
import com.dumanyusuf.tabuchallenge.domain.model.GameSettings
import com.dumanyusuf.tabuchallenge.domain.model.TeamName
import com.dumanyusuf.tabuchallenge.presentation.team_name_page.TeamNameViewModel
import com.dumanyusuf.tabuchallenge.util.Team1Border
import com.dumanyusuf.tabuchallenge.util.Team2Border
import com.google.gson.Gson
import java.net.URLEncoder
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamNamePage(
    onBackPress: () -> Unit,
    navController: NavController,
    viewModel: TeamNameViewModel= hiltViewModel()
) {
    var teamName1 by remember { mutableStateOf("") }
    var teamName2 by remember { mutableStateOf("") }

    var showGameSettingsDialog by remember { mutableStateOf(false) }
    var sliderValueTime by remember { mutableStateOf(10) }
    var sliderValueType by remember { mutableStateOf(1) }
    var sliderValueNext by remember { mutableStateOf(3) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = onBackPress,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(Color(0xFF512DA8))
                                    .size(40.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.back),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Yeni Oyun",
                                color = Color.White,
                                style = TextStyle(fontSize = 24.sp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF512DA8)
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF512DA8))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Yeni oyun için hazır mısın?",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "Takımlarınızı oluşturun ve oyuna başlayın!",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                CustomTeamInputField(
                    value = teamName1,
                    onValueChange = { teamName1 = it },
                    placeholderText = "1. Takım Adı",
                    borderBrush = Team1Border,
                    icon = R.drawable.team
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTeamInputField(
                    value = teamName2,
                    onValueChange = { teamName2 = it },
                    placeholderText = "2. Takım Adı",
                    borderBrush = Team2Border,
                    icon = R.drawable.team
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Game Settings Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFFEB3B).copy(alpha = 0.5f))
                        .padding(16.dp)
                        .clickable { showGameSettingsDialog = true }

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GameSettingItem(
                            icon = R.drawable.time,
                            value = sliderValueTime.toString(),
                            tint = Color(0xFF4CAF50)
                        )
                        GameSettingItem(
                            icon = R.drawable.type,
                            value = sliderValueType.toString(),
                            tint = Color(0xFFFFEB3B)
                        )
                        GameSettingItem(
                            icon = R.drawable.next,
                            value = sliderValueNext.toString(),
                            tint = Color(0xFFE91E63)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))


                Button(
                    onClick = {
                        viewModel.addTeamName(teamName1,teamName2)
                        viewModel.addSettinfgd(sliderValueTime,sliderValueType,sliderValueNext)

                        val gameSettings = GameSettings(gameTime = sliderValueTime, passCount = sliderValueType, roundCount = sliderValueNext)
                        val gameSettingsJson = Gson().toJson(gameSettings)

                        val teamList = listOf(
                            TeamName(id = "", teamName = teamName1, score = 0),
                            TeamName(id = "", teamName = teamName2, score = 0)
                        )
                        val teamListJson = Gson().toJson(teamList)


                        navController.navigate(Screan.GameScreanPage.route+"/$gameSettingsJson/$teamListJson")
                    },
                    enabled = teamName1.isNotEmpty()&& teamName2.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(16.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Text(
                        "Oyuna Başla",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                    }
                if (showGameSettingsDialog) {
                    GameSettingsDialog(
                        sliderValueTime = sliderValueTime,
                        onSliderValueTimeChange = { sliderValueTime = it },
                        sliderValueType = sliderValueType,
                        onSliderValueTypeChange = { sliderValueType = it },
                        sliderValueNext = sliderValueNext,
                        onSliderValueNextChange = { sliderValueNext = it },
                        onDismissRequest = { showGameSettingsDialog = false }
                    )
                }

            }
        }
    }
}


@Composable
fun GameSettingsDialog(
    sliderValueTime: Int,
    onSliderValueTimeChange: (Int) -> Unit,
    sliderValueType: Int,
    onSliderValueTypeChange: (Int) -> Unit,
    sliderValueNext: Int,
    onSliderValueNextChange: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {  },
        title = { Text("Oyun Ayarları") },
        text = {
            Column {
                // Süre Slider
                Text("Oyun Süresi: ${sliderValueTime} sn")
                Slider(
                    value = sliderValueTime.toFloat(),
                    onValueChange = {onSliderValueTimeChange(it.toInt())},
                    valueRange = 10f..90f,
                    steps = 9
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Oyun Tipi Slider
                Text("Tur Sayısı: $sliderValueType")
                Slider(
                    value = sliderValueType.toFloat(),
                    onValueChange = { onSliderValueTypeChange(it.toInt()) },
                    valueRange = 1f..5f,
                    steps = 4
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Geçme Hakkı Slider
                Text("Pas Hakkı: $sliderValueNext")
                Slider(
                    value = sliderValueNext.toFloat(),
                    onValueChange = { onSliderValueNextChange(it.toInt()) },
                    valueRange = 1f..5f,
                    steps = 4
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismissRequest

            ) {
                Text("Tamam")
            }
        }
    )
}



@Composable
fun GameSettingItem(
    icon: Int,
    value: String,
    tint: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            color = Color.White,
            style = TextStyle(fontSize = 16.sp)
        )
    }
}


@Composable
fun CustomTeamInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    borderBrush: Brush,
    icon: Int,
    iconTint: Color = Color.Yellow
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                brush = borderBrush,
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color(0xFF512DA8))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholderText, color = Color.White.copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = Color.White,
                    unfocusedPlaceholderColor = Color.White.copy(alpha = 0.6f),
                    focusedPlaceholderColor = Color.White.copy(alpha = 0.6f)
                ),
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
            )
        }
    }
}
