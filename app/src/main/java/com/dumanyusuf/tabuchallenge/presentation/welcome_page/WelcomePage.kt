package com.dumanyusuf.tabuchallenge.presentation.welcome_page

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
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
import com.dumanyusuf.tabuchallenge.util.NetworkUtils

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WelcomePage(
    navcontroller: NavController,
    context: Context = LocalContext.current
) {
    var showNoInternetDialog by remember { mutableStateOf(false)
    }

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
                        if (NetworkUtils.isInternetAvailable(context)) {
                            navcontroller.navigate(Screan.TeamNamePage.route)
                        } else {
                            showNoInternetDialog = true
                        }
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

            // İnternet bağlantısı olmadığında gösterilecek dialog
            if (showNoInternetDialog) {
                AlertDialog(
                    onDismissRequest = { showNoInternetDialog = false },
                    title = {
                        Text(
                            text = "İnternet Bağlantısı Yok",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    text = {
                        Text(
                            text = "Oyunu oynayabilmek için internet bağlantınızın olması gerekmektedir. Lütfen internet bağlantınızı kontrol edip tekrar deneyiniz.",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = { showNoInternetDialog = false }
                        ) {
                            Text(
                                text = "TAMAM",
                                color = Color(0xFF6200EE),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    containerColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
    }
}
