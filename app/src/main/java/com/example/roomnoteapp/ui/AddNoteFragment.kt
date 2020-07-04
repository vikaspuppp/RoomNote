package com.example.roomnoteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.roomnoteapp.R
import com.example.roomnoteapp.db.Note
import com.example.roomnoteapp.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.*

class AddNoteFragment : BaseFragment() {
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            edt_title.setText(note?.title)
            edt_note.setText(note?.note)
        }

        fab_save.setOnClickListener {

            val edtTitle = edt_title.text.toString().trim()
            val edtNote = edt_note.text.toString().trim()
            val note = Note(edtTitle, edtNote)
            if (this.note == null) {
                if (checkValidation(edtTitle, edtNote)) return@setOnClickListener
                addNote(note)
            } else {
                note.uid = this.note!!.uid
                updateNote(note)
            }
        }
    }

    private fun updateNote(note: Note) {
        launch {
            val deferred = async { context?.let { NoteDatabase(it).getNoteDao().updateNote(note) } }
            val result = deferred.await()
            withContext(Dispatchers.Main) {
                val message: String = if (result!! > -1) {
                    "Data updated successfully"
                } else {
                    "Sorry not able to update"
                }
                requireView().snackBar(message)
                delay(500)
                moveToHome()
            }
        }
    }

    private fun checkValidation(edtTitle: String, edtNote: String): Boolean {
        if (edtTitle.isEmpty()) {
            edt_title.error = "Title can't be empty"
            edt_title.requestFocus()
            return true
        }
        if (edtNote.isEmpty()) {
            edt_note.error = "Note can't be empty"
            edt_note.requestFocus()
            return true
        }
        return false
    }

    private fun addNote(note: Note) {
        launch {
            val deferred = async { context?.let { NoteDatabase(it).getNoteDao().add(note) } }
            val result = deferred.await()
            withContext(Dispatchers.Main) {
                val message: String = if (result!! > -1) {
                    "Data added successfully"
                } else {
                    "Sorry not able to add"
                }
                requireView().snackBar(message)
                delay(500)
                moveToHome()
            }
        }
    }

    private fun moveToHome() {
        requireView().also {
            val action = AddNoteFragmentDirections.saveNote()
            Navigation.findNavController(it).navigate(action)
        }
    }

}