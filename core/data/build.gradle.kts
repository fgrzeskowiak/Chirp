import com.google.devtools.ksp.gradle.KspAATask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import kotlin.jvm.java

plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.buildKonfig)
    alias(libs.plugins.ksp)
}

kotlin {
    // Source set declarations.
    // Declaring a target automatically creates a source set with the same name. By default, the
    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
    // common to share sources between related targets.
    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.domain)
                
                // Add KMP dependencies here

                implementation(libs.bundles.ktor.common)
                implementation(libs.touchlab.kermit)
                implementation(libs.datastore)
                implementation(libs.datastore.preferences)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                // Add Android-specific dependencies here. Note that this source set depends on
                // commonMain by default and will correctly pull the Android artifacts of any KMP
                // dependencies declared in commonMain.
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
                // Add iOS-specific dependencies here. This a source set created by Kotlin Gradle
                // Plugin (KGP) that each specific iOS target (e.g., iosX64) depends on as
                // part of KMP’s default source set hierarchy. Note that this source set depends
                // on common by default and will correctly pull the iOS artifacts of any
                // KMP dependencies declared in commonMain.
            }
        }
    }
}

android {
    buildFeatures {
        buildConfig = true
    }
}