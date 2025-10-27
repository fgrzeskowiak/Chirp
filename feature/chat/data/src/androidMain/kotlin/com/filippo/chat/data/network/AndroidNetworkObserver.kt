package com.filippo.chat.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

internal class AndroidNetworkObserver(context: Context) : NetworkObserver {
    private val connectivityManager = checkNotNull(context.getSystemService<ConnectivityManager>())

    override val isNetworkAvailable: Flow<Boolean> = callbackFlow {
        val isConnected = connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } ?: false

        trySend(isConnected)

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true)
            }

            override fun onLost(network: Network) {
                trySend(false)
            }

            override fun onUnavailable() {
                trySend(false)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities,
            ) {
                trySend(
                    networkCapabilities.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_VALIDATED
                    )
                )
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        awaitClose { connectivityManager.unregisterNetworkCallback(networkCallback) }
    }
}