package com.adelawson.youverifynotes.data.localSource

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao:TaskDao) {
    val readTasks:LiveData<List<Task>> = taskDao.readTasks()

    suspend fun addTask(task:Task){
        taskDao.addTask(task)
    }
}