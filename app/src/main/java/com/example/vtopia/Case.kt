package com.example.vtopia

import android.content.res.Resources
import android.graphics.*

class Case (_x: Float, _y: Float, _cote: Float, _sprite1: Bitmap,_sprite2: Bitmap, _state: Boolean) {

    var x = _x
    var y = _y
    var diametre = _cote
    var r = RectF(x - diametre/2, y - diametre/Math.sqrt(3.0).toFloat(), x + diametre/2, y + diametre/Math.sqrt(3.0).toFloat())
    val paint = Paint()
    var paint2 = Paint()
    var color = Color.argb(0,255,255,255)
    var sprite1 = _sprite1
    var sprite2 = _sprite2
    var state = _state

    fun draw(canvas: Canvas?) {
        // Dessine la case
        paint2.color = color
        if (state) {
            canvas?.drawBitmap(sprite1, null, r, paint)
            canvas?.drawBitmap(sprite2, null, r, paint)
        }
        else canvas?.drawOval(r, paint2)
    }

    fun changeState() {
        // Cache ou non la case
        state = !state
    }
}