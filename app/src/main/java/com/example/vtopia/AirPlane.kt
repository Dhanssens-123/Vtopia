package com.example.vtopia

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*

class AirPlane (x1: Float, y1: Float, _diametre : Float) {
    var diametre = if(_diametre < 100F) _diametre else 100F
    val random = Random()
    var color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    var r = RectF(x1, y1, x1 + diametre, y1 + diametre)
    val paint = Paint()
    var dx : Int
    var dy : Int

    init {
        if (random.nextDouble() > 0.5) dx = 1 else dx = -1
        if (random.nextDouble() < 0.5) dy = 1 else dy = -1
    }

    fun draw(canvas: Canvas?) {
        paint.color = color
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

    fun changeCouleur() {
        color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

    fun bouge(lesParois : Array<Parois>) {
        r.offset(5.0F*dx, 5.0F*dy)
        for (p in lesParois) {
            p.gereBalle(this)
        }
    }

    fun rebondit() {
        dx = -dx
        dy = -dy
        r.offset(3.0F*dx, 3.0F*dy)
    }
}