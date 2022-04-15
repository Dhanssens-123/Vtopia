package com.example.vtopia

import android.content.Context
import android.graphics.*

abstract class Icone (_x: Float, _y: Float, _w: Float, _h: Float, context: Context) {

    var x = _x
    var y = _y
    var w = _w
    var h = _h
    var r = RectF(x - w/2, y - h/2, x + w/2, y + h/2)
    val paint = Paint()
    var paint2 = Paint()

    abstract fun draw(canvas: Canvas?)

}