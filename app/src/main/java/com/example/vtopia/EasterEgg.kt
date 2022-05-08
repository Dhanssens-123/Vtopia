package com.example.vtopia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EasterEgg : AppCompatActivity() {

    lateinit private var easterEggView : EasterEggView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easter_egg)
        easterEggView = findViewById<EasterEggView>(R.id.vEasterEgg)
    }

    override fun onPause() {
        super.onPause()
        easterEggView.pause()
    }

    override fun onResume() {
        super.onResume()
        easterEggView.resume()
    }
}