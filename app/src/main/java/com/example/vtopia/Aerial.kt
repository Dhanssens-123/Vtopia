package com.example.vtopia

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

open class Aerial (_x: Float, _y: Float, _diametre : Float) {
    var x = _x
    var y = _y
    var dx = 1
    var dy = 1
    var diametre = if(_diametre < 100F) _diametre else 100F
    var r = RectF(x - diametre/2, y - diametre/2, x + diametre/2, y + diametre/2)
    val paint = Paint()

    open fun draw(canvas: Canvas?) {
        canvas?.drawOval(r, paint)
    }

    fun changeDirection(x: Boolean) {
        if (x) {
            this.dy = -dy
        }
        else {
            this.dx = -dx
        }
        r.offset(3.0F*dx, 3.0F*dy)
    }

    fun bouge(lesParois : Array<Parois>) {
        r.offset(10.0F*dx, 10.0F*dy)
        for (p in lesParois) {
            p.gereBalle(this)
        }
    }
}