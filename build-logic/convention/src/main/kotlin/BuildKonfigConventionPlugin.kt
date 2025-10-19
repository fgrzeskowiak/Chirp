import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import com.filippo.chirp.convention.pathToNamespace
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class BuildKonfigConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.codingfeline.buildkonfig")
        }

        extensions.configure<BuildKonfigExtension> {
            packageName = pathToNamespace()
            defaultConfigs {
                val apiKey = gradleLocalProperties(rootDir, rootProject.providers)
                    .getProperty("API_KEY")
                    ?: throw IllegalArgumentException("Missing API_KEY in local.properties")

                buildConfigField(
                    type = Type.STRING,
                    name = "API_KEY",
                    value = apiKey
                )
            }
        }
    }
}
