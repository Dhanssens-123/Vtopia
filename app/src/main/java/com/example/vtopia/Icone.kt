package com.example.vtopia

import android.graphics.*

class Icone (_x: Float, _y: Float, _w: Float, _h: Float, _sprite1: Bitmap, _sprite2: Bitmap?) {

    var x = _x
    var y = _y
    var w = _w
    var h = _h
    var r = RectF(x - w/2, y - h/2, x + w/2, y + h/2)
    val paint = Paint()
    var paint2 = Paint()
    var color = Color.argb(0,255,255,255)
    var sprite1 = _sprite1
    var sprite2 = _sprite2

    fun draw(canvas: Canvas?) {
        paint2.color = color
        canvas?.drawBitmap(sprite1, null, r, paint)
        if (sprite2 != null) canvas?.drawBitmap(sprite2!!, null, r, paint)
    }
}