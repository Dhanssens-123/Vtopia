package com.example.vtopia

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*

class Cloud (x: Float, y: Float, _diametre : Float) : Aerial(x,y,_diametre) {
    var color = Color.argb(255,255,255,255)
    var state = true

    override fun draw(canvas: Canvas?) {
        if (state) canvas?.drawOval(r, paint)
        paint.color = color
    }
}