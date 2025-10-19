package com.filippo.chirp.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureAndroidTarget() {
    extensions.configure<KotlinMultiplatformExtension> {
        androidTarget {
            compilerOptions {
                jvmTarget = JvmTarget.JVM_11
            }
        }
    }
}
