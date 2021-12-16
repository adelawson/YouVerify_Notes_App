package com.adelawson.youverifynotes.data.localSource

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY taskID ASC")
    fun readTasks():LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE taskName LIKE:searchQuery")
    fun searchDB(searchQuery:String):LiveData<List<Task>>


}