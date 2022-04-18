package com.example.vtopia

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
    }
    fun newGame(view : View) {
        // Commence une nouvelle partie quand le bouton est pressé
        val intent = Intent(this, MainActivity::class.java).apply {
        }
        startActivity(intent)
    }
}