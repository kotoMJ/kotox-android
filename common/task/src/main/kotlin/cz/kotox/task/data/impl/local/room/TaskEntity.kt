package cz.kotox.task.data.impl.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Task")
data class TaskEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "creation_date")
    val creationDate: String,
    @ColumnInfo(name = "due_date")
    val dueDate: String,
    @ColumnInfo(name = "encrypted_description")
    val encryptedDescription: String,
    @ColumnInfo(name = "encrypted_title")
    val encryptedTitle: String,
    @ColumnInfo(name = "encrypted_image_url")
    val encryptedImageUrl: String,
)

@Entity(tableName = "TaskImage")
data class TaskLocalImageEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "local_image_url")
    val localImageUrl: String,
)