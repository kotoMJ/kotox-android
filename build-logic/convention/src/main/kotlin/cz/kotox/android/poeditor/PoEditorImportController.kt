package cz.kotox.android.poeditor

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.kotox.android.poeditor.api.PoEditorApi
import cz.kotox.android.poeditor.api.PoEditorApiControllerImpl
import cz.kotox.android.poeditor.api.downloadUrlToString
import cz.kotox.android.poeditor.xml.AndroidXmlWriter
import cz.kotox.android.poeditor.xml.XmlPostProcessor
import cz.kotox.android.poeditor.xml.logger
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT_SECONDS = 30L
private const val READ_TIMEOUT_SECONDS = 30L
private const val WRITE_TIMEOUT_SECONDS = 30L
private const val LANGUAGE_CODE = "en-us"

class PoEditorImportController(
    val poEditorApiUrl: String,
    val poEditorApiToken: String,
    val poEditorProjectId: String,
) {


    fun executeImport(
        projectDirPath: String,
    ) {
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
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(poEditorApiUrl.toHttpUrl())
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
            code = LANGUAGE_CODE,
            tags = emptyList(),
            unquoted = true
        )

        logger.lifecycle("Downloading file from URL: $translationFileUrl")
        val translationFile = okHttpClient.downloadUrlToString(translationFileUrl)


        try {
            val xmlPostProcessor = XmlPostProcessor()

            val postProcessedXmlDocumentMap =
                xmlPostProcessor.postProcessTranslationXml(
                    translationFile
                )

            val xmlWriter = AndroidXmlWriter()

            xmlWriter.saveXml(
                projectDirPath,
                postProcessedXmlDocumentMap,
            )

//            //DEV NOTE: uncomment this to save also original file for dev purpose
//            xmlWriter.saveXmlToFolder(
//                File(File("${projectDirPath}$RESOURCES_PATH"), RESOURCES_FOLDER),
//                translationFile.toStringsXmlDocument(),
//                "strings_original"
//            )

        } catch (e: Exception) {
            logger.error(e.message)
        }

    }
}