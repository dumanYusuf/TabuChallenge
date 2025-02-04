package com.dumanyusuf.tabuchallenge.presentation.starting

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dumanyusuf.tabuchallenge.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartingPage() {

    Scaffold(
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF512DA8))
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        style = TextStyle(fontSize = 32.sp, color = Color.White),
                        text = "1. TUR",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Icon(
                        tint = Color.Yellow,
                        modifier = Modifier
                            .size(180.dp)
                            .padding(bottom = 24.dp),
                        painter = painterResource(R.drawable.smile),
                        contentDescription = ""
                    )
                    Text(
                        style = TextStyle(fontSize = 24.sp, color = Color.White),
                        text = "Takım adı gelecek",
                        modifier = Modifier.padding(bottom = 18.dp)
                    )
                    Text(
                        style = TextStyle(fontSize = 20.sp, color = Color.White.copy(alpha = 0.8f)),
                        text = "Hazırsanız oyun başlayacak. Lütfen oyunu anlatıcıya verin",
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                    Button(
                        onClick = {
                            // Oyuna başlama butonu
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .clip(RoundedCornerShape(12.dp)),
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
                }
            }
        }
    )
}
