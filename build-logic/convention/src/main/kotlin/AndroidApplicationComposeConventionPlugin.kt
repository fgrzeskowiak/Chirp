import com.android.build.api.dsl.ApplicationExtension
import com.filippo.chirp.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.filippo.convention.android.application")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        configureAndroidCompose(the<ApplicationExtension>())
    }
}
