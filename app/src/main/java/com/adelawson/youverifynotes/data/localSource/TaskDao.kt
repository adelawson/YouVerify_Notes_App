package com.adelawson.youverifynotes.data.localSource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task:Task)

    @Query("SELECT* FROM tasks_table ORDER BY")
    fun readTasks():LiveData<List<Task>>


}