package com.adelawson.youverifynotes.data.localSource

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.Duration
import java.util.*

@Parcelize
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskID:Int,
    val taskName:String,
    val taskDescription:String,
    val taskCategory:String,
    val taskPriority:String,
    val taskReminder:Boolean,
    val taskDate: String,
    val isTaskDone:Boolean,
    val taskDuration: String,
    val taskAlarmTime: String,
    val taskTimeRange: String

):Parcelable