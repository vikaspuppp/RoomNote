package com.example.roomnoteapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @ColumnInfo(name = "Title") var title: String,
    @ColumnInfo(name = "Notes") var note: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}