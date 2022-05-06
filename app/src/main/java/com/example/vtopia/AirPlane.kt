package com.example.vtopia

import android.content.Context
import android.graphics.*
import java.util.*

class AirPlane (x: Float, y: Float, _diametre : Float, context: Context) : Aerial(x,y,_diametre, context) {
    private var color = Color.argb(255,255,0,0)
    var sprite = BitmapFactory.decodeResource(context.resources, R.drawable.airplane)

    init {
        if (random.nextDouble() > 0.5) dx = 1 else dx = -1
        if (random.nextDouble() < 0.5) dy = 1 else dy = -1
    }

    override fun draw(canvas: Canvas?) {
        paint.color = color
        canvas?.drawBitmap(sprite, null, r, paint)
    }
}