package com.example.roomnoteapp.db

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun add(note: Note): Long

    @Query("Select * from Note")
    suspend fun getAllNotes(): List<Note>

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note):Int
}