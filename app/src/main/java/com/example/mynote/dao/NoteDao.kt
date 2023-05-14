package com.example.mynote.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mynote.models.NoteModel

@Dao
interface NoteDao {
    @Query("SELECT * from note_table order by id DESC")
    fun getAllNotes():List<NoteModel>

    @Insert
    fun insertNote(note: NoteModel)

    @Delete
    fun deleteNote(note: NoteModel)

    @Query("UPDATE note_table SET title = :myTitle, description = :myDescription, time = :myTime WHERE id = :myId")
    fun updateNote(myTitle: String, myDescription: String, myTime:String, myId:Int)

}