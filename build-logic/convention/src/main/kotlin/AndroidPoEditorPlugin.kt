import cz.kotox.android.poeditor.PoEditorExportController
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*

@Suppress("unused")
class AndroidPoEditorPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            poEditorPlugin()
        }
    }
}

fun Project.poEditorPlugin() {

    tasks.register("downloadPoEditorStrings") {

        val poEditorApiToken =
            System.getenv("POEDITOR_API_TOKEN")
                ?: project.property("POEDITOR_API_TOKEN") as String
        val poEditorProjectId = System.getenv("POEDITOR_PROJECT_ID")
            ?: project.property("POEDITOR_PROJECT_ID") as String

        doLast {
            PoEditorExportController(
                poEditorApiUrl = "https://api.poeditor.com/v2/",
                poEditorApiToken = poEditorApiToken,
                poEditorProjectId = poEditorProjectId
            ).executeExport()

        }
    }

}