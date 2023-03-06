package cz.kotox.android.poeditor.api


import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import retrofit2.Response

/**
 * Object passed as export options.
 */
data class Options(val unquoted: Int) {
    init {
        require(unquoted in 0..1) { "unquoted value must be 0 or 1" }
    }
}


/**
 * Basic interface used to implement API calls to the PoEditor service.
 */
interface PoEditorApiController {


    /**
     * Retrieves the translation file URL for a given project, language code, and export type.
     * Also supports a series of options.
     */
    @Suppress("LongParameterList")
    fun getTranslationFileUrl(projectId: Int,
                              code: String,
                              tags: List<String>?,
                              unquoted: Boolean): String
}


class PoEditorApiControllerImpl(private val apiToken: String,
                                private val moshi: Moshi,
                                private val poEditorApi: PoEditorApi
) : PoEditorApiController {

    private val optionsAdapter: JsonAdapter<List<Options>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, Options::class.java))



    @Suppress("LongParameterList")
    override fun getTranslationFileUrl(projectId: Int,
                                       code: String,
                                       tags: List<String>?,
                                       unquoted: Boolean): String {
        val options = listOf(
            Options(unquoted = if (unquoted) 1 else 0)
        ).let {
            optionsAdapter.toJson(it)
        }

        val response = poEditorApi.getExportFileInfo(
            apiToken = apiToken,
            id = projectId,
            type = "android_strings",
            filters = emptyList(),
            language = code,
            order = "terms",
            tags = tags,
            options = options
        ).execute()

        return response.onSuccessful { it.result.url }
    }

    private inline fun <T : PoEditorResponse, U> Response<T>.onSuccessful(func: (T) -> U): U {
        if (isSuccessful && body()?.response?.code == "200") {
            body()?.let { return func(it) }
        }

        throw IllegalStateException(
            "An error occurred while trying to retrieve data from PoEditor API: \n\n" +
                    body().toString())
    }
}
