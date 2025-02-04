package com.dumanyusuf.tabuchallenge.presentation.how_to_page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HowToPage(
    navController: NavController
) {

    Scaffold(
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF512DA8),
                                Color(0xFF673AB7)

                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nasıl Oynanır?",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    InstructionItem("Takımlarınızı oluşturun.")
                    InstructionItem("Oyun süresi, pas sayısı, tur sayısı gibi oyun ayarlarınızı özelleştirin.")
                    InstructionItem("Başlamaya hazır olduğunuzda BAŞLA butonuyla oyunu başlatın.")

                    Spacer(modifier = Modifier.height(24.dp))

                    WarningItem("Yasaklı kelime kullanmadan anlatmayı başardığınız her kelime için takımınız 1 puan kazanır.", Color.Green)
                    WarningItem("TABU yaptığınızda takımınız 2 puan kaybeder.", Color.Red)

                    Spacer(modifier = Modifier.height(32.dp))

                   Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF00C853)),
                        shape = RoundedCornerShape(16.dp),

                    ) {
                        Text(text = "Tamam", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    )
}

@Composable
fun InstructionItem(text: String) {
    Row(modifier = Modifier.padding(5.dp)) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = Color.White, fontSize = 16.sp)
    }
}

@Composable
fun WarningItem(text: String, color: Color) {
    Row(modifier = Modifier.padding(5.dp)) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = color, fontSize = 16.sp)
    }
}
