import Foundation
import ComposeApp
import UIKit
import UserNotifications
import FirebaseCore
import FirebaseMessaging

class AppDelegate : NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate, MessagingDelegate {
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        FirebaseApp.configure()
        UNUserNotificationCenter.current().delegate = self
        Messaging.messaging().delegate = self
        
        return true
    }
    
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        Messaging.messaging().apnsToken = deviceToken
        refreshToken()
    }
    
    func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: any Error) {
        print("iOS: Failed to register for push notifications: \(error.localizedDescription)")
    }
    
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        guard let token = fcmToken, !token.isEmpty else {
            refreshToken()
            return
        }
        storeToken(token)
    }
    
    func application(
        _ application: UIApplication,
        didReceiveRemoteNotification userInfo: [AnyHashable : Any],
        fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void
    ) {
        Messaging.messaging().appDidReceiveMessage(userInfo)
        completionHandler(.newData)
    }
    
    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification,
        withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void
    ) {
        completionHandler([.banner])
    }
    
    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        didReceive response: UNNotificationResponse,
        withCompletionHandler completionHandler: @escaping () -> Void
    ) {
        let userInfo = response.notification.request.content.userInfo
        
        if let chatId = userInfo["chatId"] as? String {
            let deeplinkUrl = "chirp://chat_details/\(chatId)"
            ExternalUriHandler.shared.onNewUri(uri: deeplinkUrl)
        }
        
        completionHandler()
    }
    
    private func refreshToken() {
        Task {
            do {
                let fcmToken = try await Messaging.messaging().token()
                storeToken(fcmToken)
            } catch {
                print("iOS: Error getting fcm token: \(error.localizedDescription)")
            }
        }
    }
    
    private func storeToken(_ token: String) {
        UserDefaults.standard.set(token, forKey: "FCM_TOKEN")
        IosDeviceTokenHolderBridge.shared.updateToken(token: token)
    }
}
