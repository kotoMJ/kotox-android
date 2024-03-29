package cz.kotox.common.task.poc.ui

import cz.kotox.common.task.poc.domain.model.Task
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TaskSummaryItem(
    val creationDate: LocalDateTime,
    val dueDate: LocalDateTime,
    val description: String,
    val title: String,
    val id: String,
    val localImageUrl: String?,
) {
    companion object {
        fun from(task: cz.kotox.common.task.poc.domain.model.Task) = TaskSummaryItem(
            creationDate = task.creationDate,
            dueDate = task.dueDate,
            description = task.description,
            title = task.title,
            id = task.id,
            localImageUrl = null
        )
    }
}


private const val DATE_DISPLAY_PATTERN = "dd-MM-yyyy • HH:mm"

fun LocalDateTime.toDateTimeTaskString(): String {
    return try {
        this.format(DateTimeFormatter.ofPattern(DATE_DISPLAY_PATTERN))
    } catch (e: Exception) {
        ""
    }
}