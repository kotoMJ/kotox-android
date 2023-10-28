package cz.kotox.task.domain.api.factory

import cz.kotox.common.crypto.CryptoHelper
import cz.kotox.common.task.poc.domain.model.Task
import cz.kotox.common.task.poc.data.impl.local.room.TaskEntity
import cz.kotox.common.task.poc.data.impl.remote.dto.TASK_DATE_TIME_PATTERN
import cz.kotox.common.task.poc.data.impl.remote.dto.TaskDTO
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