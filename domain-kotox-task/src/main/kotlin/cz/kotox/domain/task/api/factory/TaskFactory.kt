package cz.kotox.domain.task.api.factory

import cz.kotox.core.crypto.CryptoHelper
import cz.kotox.domain.task.api.Task
import cz.kotox.domain.task.impl.local.database.TaskEntity
import cz.kotox.domain.task.impl.remote.dto.TASK_DATE_TIME_PATTERN
import cz.kotox.domain.task.impl.remote.dto.TaskDTO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun TaskDTO.toTask() = Task(
    creationDate = creationDate.fromTaskDateTime(),
    dueDate = dueDate.fromTaskDateTime(),
    description = encryptedDescription,
    title = encryptedTitle,
    id = id,
    image = encryptedImage
)

fun TaskDTO.toTaskEntity() = TaskEntity(
    creationDate = creationDate,
    dueDate = dueDate,
    encryptedDescription = encryptedDescription,
    encryptedTitle = encryptedTitle,
    id = id,
    encryptedImageUrl = encryptedImage,
)

internal fun TaskEntity.toTask() = Task(
    creationDate = creationDate.fromTaskDateTime(),
    dueDate = dueDate.fromTaskDateTime(),
    description = CryptoHelper.decrypt(encryptedDescription),
    title = CryptoHelper.decrypt(encryptedTitle),
    id = id,
    image = CryptoHelper.decrypt(encryptedImageUrl)
)


private fun String.fromTaskDateTime(): LocalDateTime {
    val format: DateTimeFormatter = DateTimeFormatter.ofPattern(TASK_DATE_TIME_PATTERN)
    return LocalDateTime.parse(this, format)
}