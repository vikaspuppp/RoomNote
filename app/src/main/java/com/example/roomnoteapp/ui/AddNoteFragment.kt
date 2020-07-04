package com.example.roomnoteapp.ui

import android.os.Bundle
import android.view.*
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
        setHasOptionsMenu(true)
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
                addNote(note, it)
            } else {
                note.uid = this.note!!.uid
                updateNote(note, it)
            }
        }
    }

    private fun updateNote(note: Note, view: View) {
        launch {
            val deferred = async { context?.let { NoteDatabase(it).getNoteDao().updateNote(note) } }
            val result = deferred.await()
            withContext(Dispatchers.Main) {
                val message: String = if (result!! > -1) {
                    "Data updated successfully"
                } else {
                    "Sorry not able to update"
                }
                view.snackBar(message)
                delay(500)
                moveToHome(view)
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

    private fun addNote(note: Note, view: View) {
        launch {
            val deferred = async { context?.let { NoteDatabase(it).getNoteDao().add(note) } }
            val result = deferred.await()
            withContext(Dispatchers.Main) {
                val message: String = if (result!! > -1) {
                    "Data added successfully"
                } else {
                    "Sorry not able to add"
                }
                view.snackBar(message)
                delay(500)
                moveToHome(view)
            }
        }
    }

    private fun moveToHome(view: View) {
        view.also {
            Navigation.findNavController(it).navigate(AddNoteFragmentDirections.saveNote())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> if (note != null) {
                deleteNote()
            } else {
                view?.snackBar("Data is not saved in DB.")
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        launch {
            context?.let {
                NoteDatabase(it).getNoteDao().deleteNote(note!!)
                moveToHome(requireView())
            }
        }
    }

}