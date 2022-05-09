package com.example.vtopia

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mMediaPlayer: MediaPlayer? = null
    lateinit var gameView : GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameView = findViewById<GameView>(R.id.vMain)

        // Récupère le niveau défini dans le WelcomeScreen
        val level = intent.getIntExtra("level",0)
        var cityName = intent.getStringExtra("cityName")
        if (cityName == "")  cityName = "VTOPIA"
        gameView.setCityName(cityName!!)
        gameView.setLevel(level)
    }

    fun gameOnOff(v: View) {
        // Met l'activité gameView en pause
        if (gameView.isDrawing()) {
            gameView.pause()
            play_pause.setImageResource(R.drawable.play)
        }
        else {
            gameView.resume()
            play_pause.setImageResource(R.drawable.pause)
        }
    }
    fun soundOnOff(v: View) {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.theme)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
            param.setImageResource(R.drawable.sound_on)
        } else mMediaPlayer!!.stop()
    }

/*    fun soundOnOff(v: View) {
    // active ou désactive la musique
        if (gameView.isDrawing()) {
            gameView.pause()
            param.setImageResource(R.drawable.sound_on)
        }
        else {
            gameView.resume()
            param.setImageResource(R.drawable.sound_off)
        }
    }*/

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }
}