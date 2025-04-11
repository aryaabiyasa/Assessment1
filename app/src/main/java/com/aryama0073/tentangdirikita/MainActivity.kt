package com.aryama0073.tentangdirikita

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aryama0073.tentangdirikita.navigation.SetupNavGraph
import com.aryama0073.tentangdirikita.ui.theme.TentangDiriKitaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TentangDiriKitaTheme {
                SetupNavGraph()
            }
        }
    }
}

