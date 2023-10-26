package cz.kotox.common.task.poc.data.impl.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE id=:id")
    suspend fun getTask(id: String): TaskEntity

    @Query("SELECT COUNT(id) FROM task")
    suspend fun getTasksCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wallet: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(wallets: List<TaskEntity>)

    @Query("DELETE FROM task WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM task")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM taskimage WHERE id=:id")
    suspend fun getTaskLocalImage(id: String): TaskLocalImageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskLocalImage(localImageEntity: TaskLocalImageEntity)

    @Query("DELETE FROM taskimage")
    suspend fun deleteAllTaskLocalImages()

    @Transaction
    suspend fun deleteAndInsert(wallets: List<TaskEntity>) {
        deleteAllTasks()
        insertAll(wallets)
    }

}