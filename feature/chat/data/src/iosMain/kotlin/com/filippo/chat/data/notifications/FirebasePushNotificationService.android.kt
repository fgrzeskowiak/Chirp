package com.filippo.chat.data.notifications

import com.filippo.chat.domain.PushNotificationService
import com.filippo.core.domain.logging.ChirpLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIApplication
import platform.UIKit.registerForRemoteNotifications

internal class FirebasePushNotificationService : PushNotificationService {

    override val deviceToken: Flow<String?> = IosDeviceTokenHolder.token
        .onStart {
            if (IosDeviceTokenHolder.token.value == null) {
                val token = NSUserDefaults.standardUserDefaults.stringForKey("FCM_TOKEN")
                if (token != null) {
                    IosDeviceTokenHolder.updateToken(token)
                } else {
                    UIApplication.sharedApplication.registerForRemoteNotifications()
                }
            }
        }
}