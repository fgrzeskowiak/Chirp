import com.filippo.chirp.convention.compose
import com.filippo.chirp.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CmpLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.filippo.convention.kmp.library")
            apply("org.jetbrains.kotlin.plugin.compose")
            apply("org.jetbrains.compose")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets {
                commonMain {
                    dependencies {
                        implementation(compose.ui)
                        implementation(compose.foundation)
                        implementation(compose.material3)
                        implementation(compose.materialIconsExtended)

                        implementation(libs.findLibrary("koin-compose").get())
                        implementation(libs.findLibrary("koin-compose-viewmodel").get())
                    }
                }
            }
        }

        dependencies {
            "debugImplementation"(compose.uiTooling)
        }
    }
}
