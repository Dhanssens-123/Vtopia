package com.example.vtopia

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*

open class Aerial (_x: Float, _y: Float, _diametre : Float) {
    protected var x = _x
    protected var y = _y
    protected var dx = 1
    protected var dy = 1
    protected var diametre = if(_diametre < 100F) _diametre else 100F
    protected var r = RectF(x - diametre/2, y - diametre/2, x + diametre/2, y + diametre/2)
    protected val paint = Paint()
    protected val random = Random()

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
        r.offset(5.0F*dx, 5.0F*dy)
        for (p in lesParois) {
            p.gereBalle(this)
        }
    }

    fun getRect() : RectF {
        return r
    }
}