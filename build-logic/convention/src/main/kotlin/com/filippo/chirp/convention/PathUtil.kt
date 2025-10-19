package com.filippo.chirp.convention

import org.gradle.api.Project
import java.util.Locale

internal fun Project.pathToNamespace(): String {
    val namespaceSuffix = path.replace(':', '.').lowercase()

    return "com.filippo.chirp$namespaceSuffix"
}

internal fun Project.pathToResourcePrefix(): String =
    path.replace(':', '_')
        .lowercase()
        .drop(1)
        .plus("_")

internal fun Project.pathToFrameworkName(): String =
    path.split(":", "-", "_", " ")
        .joinToString(separator = "") { part ->
            part.replaceFirstChar { it.titlecase(Locale.ROOT) }
        }
