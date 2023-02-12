package cz.kotox.task.data.impl.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class, TaskLocalImageEntity::class],
    version = 1,
    exportSchema = true,
)
//@TypeConverters(Converters::class)
abstract class DomainTaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}