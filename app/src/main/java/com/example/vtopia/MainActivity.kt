package com.example.vtopia

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mMediaPlayer: MediaPlayer? = null
    private var musictheme = false
    lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameView = findViewById<GameView>(R.id.vMain)

        // Récupère le niveau défini dans le WelcomeScreen
        val level = intent.getIntExtra("level", 0)
        var cityName = intent.getStringExtra("cityName")
        if (cityName == "") cityName = "VTOPIA"
        gameView.setCityName(cityName!!)
        gameView.setLevel(level)
    }

    fun gameOnOff(v: View) {
        // Met l'activité gameView en pause
        if (gameView.isDrawing()) {
            gameView.pause()
            play_pause.setImageResource(R.drawable.play)
        } else {
            gameView.resume()
            play_pause.setImageResource(R.drawable.pause)
        }
    }

    fun playMusic() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.theme)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }
    fun pauseMusic() {
        if (mMediaPlayer?.isPlaying == true) {
            mMediaPlayer?.pause()
        }
    }
    fun soundOnOff(v: View) {
        if (musictheme == false) {
            playMusic()
            param.setImageResource(R.drawable.sound_on)
            musictheme = true}
        else {
            pauseMusic()
            param.setImageResource(R.drawable.sound_off)
            musictheme = false
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

    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }
}