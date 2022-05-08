package com.example.vtopia

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton

interface Animable {

    fun blink(txt: String, newtxt: String, counter: Int, period: Int) : String

    fun vibrate(r : RectF, vx : Float, vy : Float)

    fun changeColor(paint: Paint)
}

// Exemple liste d'éléments qui héritent pas tous de l'interface, on vérifie et on applique si ça hérite ---> Animation->Animable
