package com.example.roomnoteapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context): NoteDatabase = instance ?: synchronized(LOCK) {
            instance ?: getRoomDatabase(context).also { instance = it }
        }

        private fun getRoomDatabase(context: Context) =
            Room.databaseBuilder(context, NoteDatabase::class.java, "NoteDatabase").build()
    }
}