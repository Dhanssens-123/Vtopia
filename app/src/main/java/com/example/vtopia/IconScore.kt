package com.example.vtopia

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color

class IconScore(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icone(_x,_y,_w,_h,context) {

    // Contient le score à afficher sur l'écran, initialement nul et contrôlé par le GameManager
    var score = 0

    override fun draw(canvas: Canvas?) {
        var text = "$score"
        paint2.textSize = w
        paint2.isFakeBoldText = true
        paint2.color = Color.argb(255,52,73,94)
        // Gère l'affichage du score
        var length = paint2.measureText(text)
        var offSet = if (text.length > 2) (2*text.length-2)/(2*text.length).toFloat() else 0.5F
        canvas?.drawText(text,x - offSet*length, y + paint2.textSize/3, paint2)

    }
}