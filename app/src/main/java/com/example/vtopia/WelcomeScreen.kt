package com.example.vtopia

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome_screen.*

class WelcomeScreen() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this()

    private var level = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        var translate = AnimationUtils.loadAnimation(this, R.anim.translate)
        airplane.startAnimation(translate)
    }

    fun newGame(view: View) {
        // Commence une nouvelle partie quand le bouton est pressé
        val intent = Intent(this, MainActivity::class.java).apply {}
        intent.putExtra("level", level)
        intent.putExtra("cityName", cityName.text.toString())
        startActivity(intent)
    }

    fun toRule(view: View) {
        // Montre les règles du jeu
        startActivity(Intent(this, RulesActivity::class.java).apply {})
    }

    fun easterEgg(view: View) {
        startActivity(Intent(this, EasterEgg::class.java).apply {})
    }


    fun star(view: View) {
        var stars = arrayOf(star1, star2, star3)
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