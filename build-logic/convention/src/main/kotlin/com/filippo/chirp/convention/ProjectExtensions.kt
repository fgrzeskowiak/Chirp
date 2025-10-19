package com.filippo.chirp.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.the
import org.jetbrains.compose.*

val Project.libs: VersionCatalog
    get() = the<VersionCatalogsExtension>().named("libs")

val Project.compose: ComposePlugin.Dependencies
    get() = dependencies.extensions.getByType<ComposePlugin.Dependencies>()
