import androidx.room.gradle.RoomExtension
import com.filippo.chirp.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class RoomConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("androidx.room")
        }

        extensions.configure<RoomExtension> {
            schemaDirectory("$projectDir/schemas")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets {
                commonMain {
                    dependencies {
                        api(libs.findLibrary("androidx-room-runtime").get())
                        api(libs.findLibrary("sqlite-bundled").get())
                    }
                }
            }
        }

        dependencies {
            add("kspAndroid", libs.findLibrary("androidx-room-compiler").get())
            add("kspIosSimulatorArm64", libs.findLibrary("androidx-room-compiler").get())
            add("kspIosArm64", libs.findLibrary("androidx-room-compiler").get())
            add("kspIosX64", libs.findLibrary("androidx-room-compiler").get())
            add("kspIosSimulatorArm64", libs.findLibrary("androidx-room-compiler").get())
        }
    }
}
