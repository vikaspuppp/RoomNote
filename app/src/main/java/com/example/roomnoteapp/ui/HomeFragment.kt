package com.example.roomnoteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.roomnoteapp.R
import com.example.roomnoteapp.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setAdapter()
        fab_add.setOnClickListener {
            val action = HomeFragmentDirections.addNote()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun setAdapter() {
        rv_note.setHasFixedSize(true)
        launch {
            context?.let {
                val list = NoteDatabase(it).getNoteDao().getAllNotes()
                rv_note.layoutManager =
                    StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                rv_note.adapter = NoteAdapter(list)
            }
        }
    }
}