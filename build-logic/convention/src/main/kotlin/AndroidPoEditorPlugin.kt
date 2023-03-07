import cz.kotox.android.poeditor.PoEditorImportController
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import java.io.File
import java.util.*

@Suppress("unused")
class AndroidPoEditorPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            poEditorPlugin(
                projectDir.toString(),
                projectDir.nameWithoutExtension
            )
        }
    }
}

fun Project.poEditorPlugin(projectDir: String, moduleName: String) {

    tasks.register("downloadPoEditorStrings") {

        val poEditorApiToken =
            System.getenv("POEDITOR_API_TOKEN")
                ?: project.property("POEDITOR_API_TOKEN") as String
        val poEditorProjectId = System.getenv("POEDITOR_PROJECT_ID")
            ?: project.property("POEDITOR_PROJECT_ID") as String

        doLast {
            PoEditorImportController(
                poEditorApiUrl = "https://api.poeditor.com/v2/",
                poEditorApiToken = poEditorApiToken,
                poEditorProjectId = poEditorProjectId
            ).executeImport(
                projectDir,
                moduleName,
            )

        }
    }

}