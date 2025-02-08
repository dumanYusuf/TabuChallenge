package com.dumanyusuf.tabuchallenge.presentation.next_team_page

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NextTeamPage() {

    Scaffold(
        content = {
            Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Box(modifier = Modifier.fillMaxSize()){
                    Text(text = "2 Takım Hazırsanız Başlayaalım")
                    Button(onClick = {

                    }) {
                        Text(text = "Başla")
                    }
                }
            }
        }
    )

}