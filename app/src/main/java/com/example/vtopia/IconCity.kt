package com.example.vtopia

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color

class IconCity(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icone(_x,_y,_w,_h,context) {

    // Contient le score à afficher sur l'écran, initialement nul et contrôlé par le GameManager
    val sprite = BitmapFactory.decodeResource(context.resources, R.drawable.square_yellow)
    lateinit var cityName : String

    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(sprite, null, r, paint)

        var text = "$cityName"
        paint2.textSize = 2*w/(text.length)
        paint2.isFakeBoldText = false
        paint2.color = Color.argb(255,52,73,94)
        // Gère l'affichage du score
        var offSet = paint2.measureText(text)
        canvas?.drawText(text,x - offSet/2, y + paint2.textSize/3, paint2)
    }
}