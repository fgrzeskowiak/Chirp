import com.android.build.gradle.LibraryExtension
import com.filippo.chirp.convention.configureKotlinAndroid
import com.filippo.chirp.convention.configureKotlinMultiplatform
import com.filippo.chirp.convention.libs
import com.filippo.chirp.convention.pathToResourcePrefix
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.multiplatform")
            apply("org.jetbrains.kotlin.plugin.serialization")
            apply("com.google.devtools.ksp")
        }

        configureKotlinMultiplatform()

        extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)

            resourcePrefix = pathToResourcePrefix()
            experimentalProperties["android.experimental.kmp.enableAndroidResources"] = "true"
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets {
                androidMain {
                    dependencies {
                        implementation(libs.findLibrary("koin-android").get())
                    }
                }
                commonMain {
                    dependencies {
                        implementation(libs.findLibrary("kotlinx-serialization-json").get())
                        implementation(libs.findLibrary("kotlin-test").get())
                        implementation(libs.findLibrary("koin-core").get())
                        implementation(libs.findLibrary("koin-annotations").get())
                        implementation(libs.findLibrary("koin-annotations-jsr330").get())
                    }
                    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
                }
            }
        }

        extensions.configure<KspExtension> {
            arg("KOIN_CONFIG_CHECK", "false") // doesn't work for indirect module dependencies
            arg("KOIN_EXPORT_DEFINITIONS", "false")
        }

        dependencies {
            with(libs.findLibrary("koin-ksp-compiler").get()) {
                "kspCommonMainMetadata"(this)
                "kspAndroid"(this)
                "kspIosX64"(this)
                "kspIosArm64"(this)
                "kspIosSimulatorArm64"(this)
            }
        }

        tasks.withType<KotlinCompilationTask<*>> {
            if (name != "kspCommonMainKotlinMetadata") {
                dependsOn("kspCommonMainKotlinMetadata")
            }
        }

        Unit
    }
}
