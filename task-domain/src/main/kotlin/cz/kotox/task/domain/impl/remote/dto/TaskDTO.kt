package cz.kotox.task.domain.impl.remote.dto

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

const val TASK_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSX"

@Keep
@JsonClass(generateAdapter = true)
data class TaskDTO(
    @Json(name="creation_date")
    val creationDate: String,
    @Json(name="due_date")
    val dueDate: String,
    @Json(name="encrypted_description")
    val encryptedDescription: String,
    @Json(name="encrypted_title")
    val encryptedTitle: String,
    @Json(name="id")
    val id: String,
    @Json(name="encrypted_image")
    val encryptedImage: String
)