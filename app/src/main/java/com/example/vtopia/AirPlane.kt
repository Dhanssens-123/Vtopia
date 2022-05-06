package com.example.vtopia

import android.content.Context
import android.graphics.*
import java.util.*

class AirPlane (x: Float, y: Float,  w : Float, h : Float, context: Context) : Aerial(x,y,w,h, context) {
    private var color = Color.argb(255,255,0,0)
    var sprite = BitmapFactory.decodeResource(context.resources, R.drawable.airplane)

    init {
        if (random.nextDouble() > 0.5) dx = 1 else {
            dx = -1
            sprite = flipImageVertically(sprite)
        }
        if (random.nextDouble() < 0.5) dy = 1 else dy = -1
    }

    override fun draw(canvas: Canvas?) {
        paint.color = color
        canvas?.drawBitmap(sprite, null, r, paint)
    }

    fun bouge(lesParois : Array<Parois>) {
        r.offset(5.0F*dx, 5.0F*dy)
        for (p in lesParois) {
            p.gereBalle(this)
        }
    }

    fun changeDirection(x: Boolean) {
        if (x) {
            this.dy = -dy
        }
        else {
            this.dx = -dx
            sprite = flipImageVertically(sprite)
        }
        r.offset(3.0F*dx, 3.0F*dy)
    }

    fun flipImageVertically(bmp : Bitmap) : Bitmap {
        var matrix = Matrix().apply { postScale(-1f, 1f, bmp.width / 2f, bmp.height / 2f) }
        var newbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)
        return newbmp
    }
}