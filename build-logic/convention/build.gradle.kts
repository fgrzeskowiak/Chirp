import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.filippo.chirp.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.androidx.room.gradle.plugin)
    compileOnly(libs.buildkonfig.gradlePlugin)
    compileOnly(libs.buildkonfig.compiler)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.filippo.convention.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidComposeApplication") {
            id = "com.filippo.convention.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("cmpApplication") {
            id = "com.filippo.convention.cmp.application"
            implementationClass = "CmpApplicationConventionPlugin"
        }
        register("kmpLibrary") {
            id = "com.filippo.convention.kmp.library"
            implementationClass = "KmpLibraryConventionPlugin"
        }
        register("cmpLibrary") {
            id = "com.filippo.convention.cmp.library"
            implementationClass = "CmpLibraryConventionPlugin"
        }
        register("cmpFeatureLibrary") {
            id = "com.filippo.convention.cmp.feature"
            implementationClass = "CmpFeatureConventionPlugin"
        }
        register("buildKonfig") {
            id = "com.filippo.convention.buildKonfig"
            implementationClass = "BuildKonfigConventionPlugin"
        }
        register("room") {
            id = "com.filippo.convention.room"
            implementationClass = "RoomConventionPlugin"
        }
    }
}
