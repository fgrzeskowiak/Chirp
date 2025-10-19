package com.filippo.chirp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var showSplashScreen = true
        installSplashScreen().setKeepOnScreenCondition { showSplashScreen }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(
                onStartDestinationReady = {
                    showSplashScreen = false
                }
            )
        }
    }
}
