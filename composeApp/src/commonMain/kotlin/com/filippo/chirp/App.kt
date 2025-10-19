package com.filippo.chirp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.filippo.chirp.di.KoinApp
import com.filippo.chirp.navigation.DeepLinkListener
import com.filippo.chirp.navigation.NavigationRoot
import com.filippo.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration
import org.koin.ksp.generated.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App(onStartDestinationReady: () -> Unit = {}) {
    KoinMultiplatformApplication(koinConfiguration(KoinApp.koinConfiguration())) {
        val navController = rememberNavController()
        val viewModel = koinViewModel<MainViewModel>()
        val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

        DeepLinkListener(navController)

        LaunchedEffect(startDestination) {
            if (startDestination != null) {
                onStartDestinationReady()
            }
        }

        ChirpTheme {
            startDestination?.let {
                NavigationRoot(
                    navController = navController,
                    startDestination = it
                )
            }
        }
    }
}
