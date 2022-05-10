package com.example.vtopia

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mMediaPlayer: MediaPlayer? = null
    private var musicTheme = true
    lateinit private var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameView = findViewById<GameView>(R.id.vMain)

        // Récupère le niveau et le nom de la ville définis dans le WelcomeScreen
        val level = intent.getIntExtra("level", 0)
        var cityName = intent.getStringExtra("cityName")
        if (cityName == "") cityName = "VTOPIA"
        gameView.setCityName(cityName!!)
        gameView.setLevel(level)

        playMusic()
    }

    fun gameOnOff() {
        // Gère l'état de l'activité gameView
        if (gameView.isDrawing()) {
            gameView.pause()
            pauseMusic()
            play_pause.setImageResource(R.drawable.play)
        } else {
            gameView.resume()
            playMusic()
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

    fun soundOnOff() {
        // Gère le contrôle du son
        if (musicTheme == false) {
            playMusic()
            param.setImageResource(R.drawable.sound_on)
            musicTheme = true}
        else {
            pauseMusic()
            param.setImageResource(R.drawable.sound_off)
            musicTheme = false
        }
    }

    override fun onPause() {
        super.onPause()
        pauseMusic()
        gameView.pause()
    }

    override fun onResume() {
        super.onResume()
        playMusic()
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