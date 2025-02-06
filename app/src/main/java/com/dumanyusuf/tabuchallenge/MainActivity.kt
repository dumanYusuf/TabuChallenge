package com.dumanyusuf.tabuchallenge

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.dumanyusuf.tabuchallenge.presentation.navigation.PageController
import com.dumanyusuf.tabuchallenge.ui.theme.TabuChallengeTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            TabuChallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    PageController()
                }
            }
        }
    }
}