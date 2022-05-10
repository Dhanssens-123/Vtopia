package com.example.vtopia

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*

open class Aerial (_x: Float, _y: Float, _w: Float, _h: Float) { 

    protected var x = _x
    protected var y = _y
    protected var dx = 1
    protected var dy = 1
    protected var w = _w
    protected var h = _h
    protected var r = RectF(x - w/2, y - h/2, x + w/2, y + h/2)
    protected val paint = Paint()
    protected val random = Random()

    open fun draw(canvas: Canvas?) {
        canvas?.drawOval(r, paint)
    }

    fun getRect() : RectF {
        return r
    }
}