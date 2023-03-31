package cz.kotox.android.poeditor.api

import cz.kotox.android.poeditor.api.ExportResponse
import cz.kotox.android.poeditor.api.ListLanguagesResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PoEditorApi {

    /**
     * Returns a list of languages that the current PoEditor project contains.
     */
    @FormUrlEncoded
    @POST("languages/list")
    fun getProjectLanguages(@Field("api_token") apiToken: String,
                            @Field("id") id: Int): Call<ListLanguagesResponse>

    /**
     * Returns the exportables ready to retrieve from the current PoEditor project.
     */
    @Suppress("LongParameterList")
    @FormUrlEncoded
    @JvmSuppressWildcards
    @POST("projects/export")
    fun getExportFileInfo(@Field("api_token") apiToken: String,
                          @Field("id") id: Int,
                          @Field("language") language: String,
                          @Field("type") type: String,
                          @Field("filters") filters: List<String>? = null,
                          @Field("order") order: String? = null,
                          @Field("tags") tags: List<String>? = null,
                          @Field("options") options: String? = null): Call<ExportResponse>
}
