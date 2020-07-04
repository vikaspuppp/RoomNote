package com.example.roomnoteapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.roomnoteapp.R
import com.example.roomnoteapp.db.Note
import kotlinx.android.synthetic.main.cell_note.view.*

class NoteAdapter(private val list: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    class NoteHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var note: Note? = null
        fun bind(note: Note) {
            this.note = note
            view.cell_tv_title.text = note.title
            view.cell_tv_note.text = note.note
            view.setOnClickListener {
                val action = HomeFragmentDirections.addNote(note)
                Navigation.findNavController(view).navigate(action)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder =
        NoteHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_note, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(list[position])
    }
}