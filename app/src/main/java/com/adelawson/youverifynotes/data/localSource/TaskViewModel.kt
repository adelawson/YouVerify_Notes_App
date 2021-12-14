package com.adelawson.youverifynotes.data.localSource

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(app: Application): AndroidViewModel(app) {

    private val readTasks:LiveData<List<Task>>
    private val repository:TaskRepository

    init {
        val taskDao = TasksDatabase.getDB(app).taskDao()
        repository = TaskRepository(taskDao)
        readTasks = repository.readTasks
    }

    fun addTask(task:Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTask(task)
        }
    }

}