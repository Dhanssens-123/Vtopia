package com.example.vtopia

import android.graphics.Paint
import android.graphics.RectF

interface Animable {

    fun blink(txt: String, newtxt: String, counter: Int, period: Int) : String

    fun vibrate(rect : RectF, vx : Float, vy : Float)

    fun changeColor(paint: Paint)
}

// Exemple liste d'éléments qui héritent pas tous de l'interface, on vérifie et on applique si ça hérite ---> Animation->Animable
