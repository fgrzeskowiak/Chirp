package com.filippo.chirp.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) = with(commonExtension) {
    buildFeatures {
        compose = true
    }

    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").get()
        add("implementation", platform(bom))
        add("testImplementation", platform(bom))
        add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
    }
}
