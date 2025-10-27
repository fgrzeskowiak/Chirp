package com.filippo.chat.data.lifecycle

import jakarta.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSNotificationName
import platform.Foundation.NSOperationQueue
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationDidBecomeActiveNotification
import platform.UIKit.UIApplicationDidEnterBackgroundNotification
import platform.UIKit.UIApplicationState
import platform.UIKit.UIApplicationWillEnterForegroundNotification
import platform.UIKit.UIApplicationWillResignActiveNotification
import platform.darwin.NSObjectProtocol

@Singleton
class IosAppLifecycleObserver: AppLifecycleObserver {
    private val notificationCenter = NSNotificationCenter.defaultCenter

    override val isInForeground: Flow<Boolean> = callbackFlow {
        val currentAppState = UIApplication.sharedApplication.applicationState
        val isInForeground = when (currentAppState) {
            UIApplicationState.UIApplicationStateActive,
            UIApplicationState.UIApplicationStateInactive,
                -> true// app partially covered
            else -> false
        }

        trySend(isInForeground)

        val observers = listOf(
            notificationCenter
                .createObserver(UIApplicationDidBecomeActiveNotification) {
                    trySend(true)
                },
            notificationCenter
                .createObserver(UIApplicationWillEnterForegroundNotification) {
                    trySend(true)
                },
            notificationCenter
                .createObserver(UIApplicationDidEnterBackgroundNotification) {
                    trySend(false)
                },
            notificationCenter
                .createObserver(UIApplicationWillResignActiveNotification) {
                    trySend(false)
                }
        )

        awaitClose {
            observers.forEach(notificationCenter::removeObserver)
        }
    }

    private fun NSNotificationCenter.createObserver(
        name: NSNotificationName,
        onNotificationReceived: () -> Unit,
    ): NSObjectProtocol =
        addObserverForName(
            name = name,
            `object` = null,
            queue = NSOperationQueue.mainQueue,
        ) {
            onNotificationReceived()
        }
}