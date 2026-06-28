package com.example.eugenpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.eugenpro.presentation.mainScreen.MainScreen
import com.example.eugenpro.presentation.navigation.AppNavigation
import com.example.eugenpro.presentation.mainScreen.MainScreenViewModels
import com.example.eugenpro.ui.theme.EugenProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: MainScreenViewModels by viewModels()
        setContent {
            EugenProTheme {
                AppNavigation()
            }
        }
    }
}