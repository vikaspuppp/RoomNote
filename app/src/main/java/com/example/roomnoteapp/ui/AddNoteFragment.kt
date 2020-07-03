package com.example.roomnoteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomnoteapp.R
import com.example.roomnoteapp.db.Note
import com.example.roomnoteapp.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNoteFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fab_save.setOnClickListener {

            val edtTitle = edt_title.text.toString().trim()
            val edtNote = edt_note.text.toString().trim()
            if (checkValidation(edtTitle, edtNote)) return@setOnClickListener
            val note = Note(edtTitle, edtNote)
            addNote(note)
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
                requireView().snackbar(message)
            }
        }
    }

}