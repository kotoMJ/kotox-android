import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.kotox.android.poeditor.api.PoEditorApi
import cz.kotox.android.poeditor.api.PoEditorApiControllerImpl
import cz.kotox.android.poeditor.api.downloadUrlToString
import cz.kotox.android.poeditor.xml.AndroidXmlWriter
import cz.kotox.android.poeditor.xml.XmlPostProcessor
import cz.kotox.android.poeditor.xml.toStringsXmlDocument
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.gradle.api.Plugin
import org.gradle.api.Project
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

@Suppress("unused")
class AndroidPoEditorPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            poe()
        }
    }
}

private const val POEDITOR_API_URL = "https://api.poeditor.com/v2/"
private const val CONNECT_TIMEOUT_SECONDS = 30L
private const val READ_TIMEOUT_SECONDS = 30L
private const val WRITE_TIMEOUT_SECONDS = 30L

fun Project.poe() {

    tasks.register("downloadPoEditorStrings") {

        val poEditorApiToken =
            System.getenv("POEDITOR_API_TOKEN")
                ?: project.property("POEDITOR_API_TOKEN") as String
        val poEditorProjectId = System.getenv("POEDITOR_PROJECT_ID")
            ?: project.property("POEDITOR_PROJECT_ID") as String

        doLast {

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(
                    HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                        override fun log(message: String) {
                            logger.debug(message)
                        }
                    })
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                //.add(Date::class.java, PoEditorDateJsonAdapter())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(POEDITOR_API_URL.toHttpUrl())
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

            val poEditorApi: PoEditorApi = retrofit.create(PoEditorApi::class.java)


            val poEditorApiController = PoEditorApiControllerImpl(
                apiToken = poEditorApiToken,
                moshi = moshi,
                poEditorApi = poEditorApi
            )

            val translationFileUrl = poEditorApiController.getTranslationFileUrl(
                projectId = poEditorProjectId.toInt(),
                code = "en-us",
                tags = emptyList(),
                unquoted = true
            )

            logger.lifecycle("Downloading file from URL: $translationFileUrl")
            val translationFile = okHttpClient.downloadUrlToString(translationFileUrl)


            try {
                val xmlPostProcessor = XmlPostProcessor()

//             Extract final files from downloaded translation XML
                val postProcessedXmlDocumentMap =
                    xmlPostProcessor.postProcessTranslationXml(
                        translationFile
                    )

                val xmlWriter = AndroidXmlWriter()

                xmlWriter.saveXml(
                    "./",
                    postProcessedXmlDocumentMap,
                    "en_us",
                    "en_us",
                    emptyMap(),
                )

                xmlWriter.saveXmlToFolder(
                    File(File("./"), "values"),
                    translationFile.toStringsXmlDocument(),
                    "strings_original"
                )
            } catch (e: Exception) {
                logger.error(e.message)
            }

        }
    }

}