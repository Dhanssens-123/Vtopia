package com.example.vtopia

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.TypedValue

class IconTime(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icone(_x,_y,_w,_h,context), Animation {

    // Contient le timer à afficher sur l'écran contrôlé par le GameManager
    var time = 0.0
    var blink = false
    var switch = 0

    fun changeTime(newTime: Double) {
        time = newTime
    }

    override fun draw(canvas: Canvas?) {
        paint.textSize = w
        paint.isFakeBoldText = true
        paint.color = Color.argb(255,52,73,94)
        // Gère l'affichage du timer
        var formatted_time = String.format("%d", time.toInt())
        if (time.toInt() % 10 == 0) {
            formatted_time = blink(formatted_time, switch)
            switch += 2
        } else switch = 0
        var offSet = paint.measureText(formatted_time)
        canvas?.drawText(formatted_time, x - offSet/2, y + paint.textSize/3, paint)
    }
}