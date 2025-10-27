import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.convention.cmp.application)
    alias(libs.plugins.compose.hot.reload)
    alias(libs.plugins.ksp)
}

kotlin {
    sourceSets {
        androidMain {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.koin.android)
                implementation(libs.core.splashscreen)
            }
        }
        commonMain {
            dependencies {
                implementation(projects.core.data)
                implementation(projects.core.domain)
                implementation(projects.core.designsystem)
                implementation(projects.core.presentation)

                implementation(projects.feature.auth.domain)
                implementation(projects.feature.auth.presentation)

                implementation(projects.feature.chat.domain)
                implementation(projects.feature.chat.data)
                implementation(projects.feature.chat.database)
                implementation(projects.feature.chat.presentation)

                implementation(libs.jetbrains.compose.navigation)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.jetbrains.compose.viewmodel)
                implementation(libs.jetbrains.compose.runtime)
            }
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.filippo.chirp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.filippo.chirp"
            packageVersion = "1.0.0"
        }
    }
}
