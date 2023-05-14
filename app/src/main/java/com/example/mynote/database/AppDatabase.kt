package com.example.mynote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynote.dao.NoteDao
import com.example.mynote.models.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object{
        var INSTANCE: AppDatabase?=null
        fun initDatabase(context: Context):AppDatabase{
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "My Database"
            ).build()
            INSTANCE = instance
            return instance
        }
    }
}