package com.example.vtopia

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome_screen.*

class WelcomeScreen() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this()

    var level = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
    }

    fun newGame(view: View) {
        // Commence une nouvelle partie quand le bouton est pressé
        val intent = Intent(this, MainActivity::class.java).apply {}
        intent.putExtra("level", level)
        startActivity(intent)
    }

    fun tips(view: View) {
        // Montre les règles du jeu
        startActivity(Intent(this, Tips::class.java).apply {})
    }

    fun easterEgg(view: View) {
        startActivity(Intent(this, EasterEgg::class.java).apply {})
    }

    fun star(view: View) {
        level = (level + 1) % 4
        var stars = arrayOf(star1, star2, star3)
        for (star in stars) star.setImageResource(R.drawable.star_bord)
        for (i in 0..(level-1)) {
            stars[i].setImageResource(R.drawable.star_yellow)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WelcomeScreen> {
        override fun createFromParcel(parcel: Parcel): WelcomeScreen {
            return WelcomeScreen(parcel)
        }

        override fun newArray(size: Int): Array<WelcomeScreen?> {
            return arrayOfNulls(size)
        }
    }
}