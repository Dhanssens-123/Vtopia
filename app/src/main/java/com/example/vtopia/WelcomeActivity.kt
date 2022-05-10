package com.example.vtopia

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome_screen.*

class WelcomeActivity() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this()

    private var mMediaPlayer: MediaPlayer? = null

    private var level = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        // Anime un avion au lancement de l'application
        val translate = AnimationUtils.loadAnimation(this, R.anim.translate)
        airplane.startAnimation(translate)
    }

    fun newGame(v: View) {
        // Commence une nouvelle partie quand le bouton est pressé
        pauseMusic()

        val intent = Intent(this, MainActivity::class.java).apply {}
        intent.putExtra("level", level)
        intent.putExtra("cityName", cityName.text.toString())
        startActivity(intent)
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

    fun restartMusic() {
        mMediaPlayer = null
    }

    fun toRule(v: View) {
        // Montre les règles du jeu
        pauseMusic()
        startActivity(Intent(this, RulesActivity::class.java).apply {})
    }

    fun easterEgg(v: View) {
        pauseMusic()
        startActivity(Intent(this, EasterEggActivity::class.java).apply {})
    }


    fun star(view: View) {
        // Modifie le niveau de la partie et affiche ce dernier à l'écran
        // grâce à des étoiles
        val stars = arrayOf(star1, star2, star3)
        for (star in stars) star.setImageResource(R.drawable.star_bord)
        when(view.id) {
            R.id.star1 -> {
                level = 1
                star1.setImageResource(R.drawable.star_yellow)
            }
            R.id.star2 -> {
                level = 2
                star1.setImageResource(R.drawable.star_yellow)
                star2.setImageResource(R.drawable.star_yellow)
            }
            R.id.star3 -> {
                level = 3
                star1.setImageResource(R.drawable.star_yellow)
                star2.setImageResource(R.drawable.star_yellow)
                star3.setImageResource(R.drawable.star_yellow)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        pauseMusic()
    }

    override fun onResume() {
        super.onResume()
        restartMusic()
        playMusic()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WelcomeActivity> {
        override fun createFromParcel(parcel: Parcel): WelcomeActivity {
            return WelcomeActivity(parcel)
        }

        override fun newArray(size: Int): Array<WelcomeActivity?> {
            return arrayOfNulls(size)
        }
    }
}