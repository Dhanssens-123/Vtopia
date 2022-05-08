package com.example.vtopia

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.util.TypedValue

class IconTime(_x: Float, _y: Float, _w: Float, _h: Float) : Icone(_x,_y,_w,_h), Animation {

    // Contient le timer à afficher sur l'écran contrôlé par le GameManager
    private var time = 0.0
    private var switch = 0

    init {
        paint.textSize = w
        paint.isFakeBoldText = true
        paint.color = Color.argb(255,52,73,94)
    }

    fun changeTime(newTime: Double) {
        time = newTime
    }

    override fun draw(canvas: Canvas?) {
        var formatted_time = String.format("%d", time.toInt())

        if (time.toInt() % 10 == 0) {
            formatted_time = blink(formatted_time, "", switch)
            switch += 2
        } else switch = 0

        var offSet = paint.measureText(formatted_time)
        canvas?.drawText(formatted_time, x - offSet/2, y + paint.textSize/3, paint)
    }

    override  fun blink(txt: String, newtxt: String, flag: Int) : String {
        return if ((flag % 20) < 10) newtxt else txt
    }

    override fun vibrate(r: RectF, vx: Float, vy: Float) {
        TODO("Not yet implemented")
    }
}