package com.example.mobprogtk4.utility

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun ToastNotification(message: String) {
    val context = LocalContext.current

    LaunchedEffect(context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}