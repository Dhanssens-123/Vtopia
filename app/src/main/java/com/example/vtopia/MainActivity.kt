package com.example.vtopia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import android.widget.ZoomControls
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var gameView : GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameView = findViewById<GameView>(R.id.vMain)

        // Récupère le niveau défini dans le WelcomeScreen
        val level = intent.getIntExtra("level",0)
    }

    fun gameOnOff(v: View) {
        // Met l'activité gameView en pause
        if (gameView.drawing) {
            gameView.pause()
            play_pause.setImageResource(R.drawable.pause)
        }
        else {
            gameView.resume()
            play_pause.setImageResource(R.drawable.play)
        }
    }

    fun soundOnOff(v: View) {
        // Met également l'activité gameView en pause
        if (gameView.drawing) {
            gameView.pause()
            param.setImageResource(R.drawable.outline)
        }
        else {
            gameView.resume()
            param.setImageResource(R.drawable.param)
        }
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }
}