package com.example.vtopia

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas

class IconLevel(_x: Float, _y: Float, _w: Float, _h: Float, context: Context, _isFull: Boolean) : Icone(_x,_y,_w,_h) {

    // Attribue une image en fonction du type de l'icône de niveau
    private var isFull = _isFull
    private val spriteSet = mapOf<String, Bitmap>(
        "bord" to BitmapFactory.decodeResource(context.resources, R.drawable.star_bord),
        "star" to BitmapFactory.decodeResource(context.resources, R.drawable.star_yellow)
    )

    override fun draw(canvas: Canvas?) {
        // Dessine l'icône selon son type
        if (isFull) {
            canvas?.drawBitmap(spriteSet["star"]!!, null, r, paint)
            canvas?.drawBitmap(spriteSet["bord"]!!, null, r, paint)
        } else {
            canvas?.drawBitmap(spriteSet["bord"]!!, null, r, paint)
        }
    }
}