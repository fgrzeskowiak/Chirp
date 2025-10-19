import com.filippo.chirp.convention.configureAndroidTarget
import com.filippo.chirp.convention.configureIosTarget
import com.filippo.chirp.convention.libs
import com.google.devtools.ksp.gradle.KspAATask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

class CmpApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.filippo.convention.android.application.compose")
            apply("org.jetbrains.kotlin.multiplatform")
            apply("org.jetbrains.compose")
            apply("org.jetbrains.kotlin.plugin.compose")
            apply("org.jetbrains.kotlin.plugin.serialization")
            apply("com.google.devtools.ksp")
        }

        configureAndroidTarget()
        configureIosTarget()

        extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
            sourceSets {
                commonMain {
                    dependencies {
                        implementation(libs.findLibrary("koin-core").get())
                        implementation(libs.findLibrary("koin-annotations").get())
                        implementation(libs.findLibrary("koin-annotations-jsr330").get())
                        implementation(libs.findLibrary("koin-compose").get())
                        implementation(libs.findLibrary("koin-compose-viewmodel").get())
                    }
                }
            }
        }

        dependencies {
            with(libs.findLibrary("koin-ksp-compiler").get()) {
                "kspCommonMainMetadata"(this)
                "kspAndroid"(this)
                "kspIosX64"(this)
                "kspIosArm64"(this)
                "kspIosSimulatorArm64"(this)
            }
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        }

        tasks.withType<KotlinCompilationTask<*>> {
            if (name != "kspCommonMainKotlinMetadata") {
                dependsOn("kspCommonMainKotlinMetadata")
            }
        }

        Unit
    }
}
