package com.adelawson.youverifynotes.data.localSource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class TasksDatabase:RoomDatabase() {

    abstract fun taskDao(): TaskDao
    companion object{
        @Volatile
        private var instance:TasksDatabase? = null
        fun getDB(context: Context):TasksDatabase{
            val tempInstance = instance
            if (tempInstance!=null ){
                return tempInstance
            }
            synchronized(this){
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDatabase::class.java, "tasks_database"
                ).build()
                instance = inst
                return inst
            }
        }
    }

}