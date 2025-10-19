import com.filippo.chirp.convention.compose
import com.filippo.chirp.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CmpFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.filippo.convention.cmp.library")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets {
                commonMain {
                    dependencies {
                        implementation(project(":core:presentation"))
                        implementation(project(":core:designsystem"))
                        implementation(dependencies.platform(libs.findLibrary("koin-bom").get()))
                        implementation(libs.findLibrary("koin-compose").get())

                        implementation(libs.findLibrary("jetbrains-compose-viewmodel").get())
                        implementation(libs.findLibrary("jetbrains-compose-navigation").get())
                        implementation(libs.findLibrary("jetbrains-lifecycle-viewmodel").get())
                        implementation(libs.findLibrary("jetbrains-lifecycle-viewmodel-savedstate").get())
                        implementation(libs.findLibrary("jetbrains-lifecycle-compose").get())
                        implementation(libs.findLibrary("jetbrains-savedstate").get())
                        implementation(libs.findLibrary("jetbrains-bundle").get())

                        implementation(compose.components.resources)
                        implementation(compose.components.uiToolingPreview)
                    }
                }
                androidMain {
                    dependencies {
                        implementation(dependencies.platform(libs.findLibrary("koin-bom").get()))
                        implementation(libs.findLibrary("koin-android").get())
                        implementation(libs.findLibrary("koin-core-viewmodel").get())
                        implementation(libs.findLibrary("koin-androidx-compose").get())
                        implementation(libs.findLibrary("koin-androidx-navigation").get())
                    }
                }
            }
        }
    }
}
