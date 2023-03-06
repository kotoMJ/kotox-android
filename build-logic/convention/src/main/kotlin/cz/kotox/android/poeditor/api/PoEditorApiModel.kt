package cz.kotox.android.poeditor.api
import java.util.Date


data class ResponseStatus(val status: String,
                          val code: String,
                          val message: String)

open class PoEditorResponse(open val response: ResponseStatus)

data class ListLanguagesResponse(override val response: ResponseStatus,
                                 val result: ListLanguagesResult
) : PoEditorResponse(response)


data class ProjectLanguage(val name: String,
                           val code: String,
                           val translations: Int,
                           val percentage: Double,
                           val updated: Date?)

data class ListLanguagesResult(val languages: List<ProjectLanguage>)

data class ExportResult(val url: String)

data class ExportResponse(override val response: ResponseStatus,
                          val result: ExportResult
) : PoEditorResponse(response)