package com.example.roomnoteapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.roomnoteapp.R
import com.example.roomnoteapp.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
        fab_add.setOnClickListener {
            val action = HomeFragmentDirections.addNote()
            Navigation.findNavController(it).navigate(action)
            getNotes()
        }
    }

    private fun getNotes() {
        launch {
            val deferred = async { NoteDatabase(requireContext()).getNoteDao().getAllNotes() }
            val list = deferred.await()
            Log.d("NoteList", "getNotes: ${list.toString()}")
        }
    }
}