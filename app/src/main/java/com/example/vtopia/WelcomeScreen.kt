package com.example.vtopia

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WelcomeScreen() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
    }

    fun newGame(view: View) {
        // Commence une nouvelle partie quand le bouton est pressé
        startActivity(Intent(this, MainActivity::class.java).apply {})
    }

    fun tips(view: View) {
        // Montre les règles du jeu
        startActivity(Intent(this, Tips::class.java).apply {})
        fun easterEgg(view: View) {
            startActivity(Intent(this, EasterEgg::class.java).apply {})
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