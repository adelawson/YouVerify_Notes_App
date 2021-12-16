package com.adelawson.youverifynotes.data.localSource

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao:TaskDao) {
    val readTasks:LiveData<List<Task>> = taskDao.readTasks()

    fun addTask(task: Task){
        taskDao.addTask(task)
    }

    fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }
}