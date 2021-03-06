package com.example.vtopia

import android.content.Context
import android.graphics.*

class AirPlane (x: Float, y: Float,  w: Float, h: Float, context: Context) : Aerial(x,y,w,h) {

    private var sprite = BitmapFactory.decodeResource(context.resources, R.drawable.airplane)
    private var VITESSE = 5.0F
    private var IMPULSION = 3.0F

    init {
        if (random.nextDouble() > 0.5) dx = 1 else {
            dx = -1
            sprite = flipImageVertically(sprite)
        }
        if (random.nextDouble() < 0.5) dy = 1 else dy = -1
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(sprite, null, r, paint)
    }

    fun bouge(lesParois: Array<Parois>) {
        r.offset(VITESSE*dx, VITESSE*dy)
        for (p in lesParois) {
            p.gereAirPlane(this) // Vérifie si l'avion percute les bords de l'écran
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
        // Donne une impulsion à l'avion pour qu'il s'échappe des bords
        r.offset(IMPULSION*dx, IMPULSION*dy)
    }

    fun flipImageVertically(bmp: Bitmap) : Bitmap {
        // Retourne verticalement l'image
        val matrix = Matrix().apply { postScale(-1f, 1f, bmp.width / 2f, bmp.height / 2f) }
        val newbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)
        return newbmp
    }
}