package com.adelawson.youverifynotes.data.localSource

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tasks_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskID:Int,
    val taskName:String,
    val taskDescription:String,
    val taskCategory:String,
    val taskPriority:String,
    val taskReminder:Boolean
)