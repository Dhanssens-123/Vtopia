package com.example.vtopia

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RulesActivity : AppCompatActivity() {

    private var mMediaPlayer: MediaPlayer? = null

    lateinit private var tipsView : RulesView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)
        tipsView = findViewById<RulesView>(R.id.vTips)

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
        tipsView.pause()
    }

    override fun onResume() {
        super.onResume()
        playMusic()
        tipsView.resume()
    }
}