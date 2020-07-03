package com.example.roomnoteapp.ui

import android.view.View
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(msg: String) = Snackbar.make(this, msg, BaseTransientBottomBar.LENGTH_LONG).show()