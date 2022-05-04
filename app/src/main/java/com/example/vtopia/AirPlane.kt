package com.example.vtopia

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*

class AirPlane (x: Float, y: Float, _diametre : Float) : Aerial(x,y,_diametre) {
    val random = Random()
    var color = Color.argb(255,255,0,0)

    init {
        if (random.nextDouble() > 0.5) dx = 1 else dx = -1
        if (random.nextDouble() < 0.5) dy = 1 else dy = -1
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        paint.color = color
    }
}