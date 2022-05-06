package com.example.vtopia

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Parois (x1: Float, y1: Float, x2: Float, y2: Float) {
    private val r = RectF(x1, y1, x2, y2)
    private val paint = Paint()

    fun draw(canvas: Canvas) {
        paint.color = Color.argb(0,0,0,0)
        canvas.drawRect(r, paint)
    }

    fun gereBalle(b: Aerial) {
        if (RectF.intersects(r, b.getRect())) {
            if (r.width() > r.height()) {
                b.changeDirection (true)
            }
            else {
                b.changeDirection(false)
            }
        }
    }

}