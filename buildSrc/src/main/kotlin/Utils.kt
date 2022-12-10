import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.Project
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun generateArtifactName(defaultConfig: DefaultConfig, project: Project): String {
    val date = SimpleDateFormat("yyyyMMdd", Locale.US).format(Date())
    return "${defaultConfig.applicationId}-${project.name}-${defaultConfig.versionName}-${defaultConfig.versionCode}-$date"
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}