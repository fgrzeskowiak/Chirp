import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    var body : some Scene {
        WindowGroup {
             ContentView()
                .onOpenURL { url in
                    ExternalUriHandler.shared.onNewUri(uri: url.absoluteString)
                }
        }
    }
}
