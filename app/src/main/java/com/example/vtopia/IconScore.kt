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
        paint2.textSize = w
        paint2.isFakeBoldText = true
        paint2.color = Color.argb(255,52,73,94)
        // Gère l'affichage du score
        var text = "$score"
        var offSet = paint.measureText(text)
        canvas?.drawText(text,x - offSet/2, y + paint2.textSize/3, paint2)
    }
}