package cz.kotox.task.domain.api.model

import java.time.LocalDateTime

data class Task(
    val creationDate: LocalDateTime,
    val dueDate: LocalDateTime,
    val description: String,
    val title: String,
    val id: String,
    val image: String
)