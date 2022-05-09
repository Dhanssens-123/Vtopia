package com.example.vtopia

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EasterEggActivity : AppCompatActivity() {

    private var mMediaPlayer: MediaPlayer? = null
    private var musictheme = true

    lateinit private var easterEggView : EasterEggView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easter_egg)
        easterEggView = findViewById<EasterEggView>(R.id.vEasterEgg)

        playMusic()
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

    override fun onPause() {
        super.onPause()
        pauseMusic()
        easterEggView.pause()
    }

    override fun onResume() {
        super.onResume()
        playMusic()
        easterEggView.resume()
    }
}