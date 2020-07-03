package com.example.roomnoteapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.roomnoteapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var navigationController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationController = Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this,navigationController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navigationController,null)

    }
}