package com.example.vtopia

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.TypedValue

class IconTime(_x: Float, _y: Float, _w: Float, _h: Float) : Icone(_x,_y,_w,_h), Animable {

    // Contient le timer à afficher sur l'écran contrôlé par le GameManager
    private var time = 0.0
    private var counter = 0
    private var BLINK_PERIOD = 20 // Divisible par 2
    private var MULTICOLOR_PERIOD = 4 // Divisible par 2

    fun changeTime(newTime: Double) {
        time = newTime
    }

    override fun draw(canvas: Canvas?) {
        var formatted_time = String.format("%d", time.toInt())

        if (time.toInt() % 10 == 0 || time.toInt() < 10) {
            formatted_time = blink(formatted_time, "", counter, BLINK_PERIOD)
            counter += 1
        } else counter = 0

        if (time.toInt() <= 5) {
            paintText.textSize = 1.5f*w
            if (counter%MULTICOLOR_PERIOD == MULTICOLOR_PERIOD/2) changeColor(paintText)
        }

        var offSet = paintText.measureText(formatted_time)
        canvas?.drawText(formatted_time, x - offSet/2, y + paintText.textSize/3, paintText)
    }

    fun reset() {
        paintText.color = Color.argb(255,52,73,94)
        paintText.textSize = r.width()
    }

    override  fun blink(txt: String, newtxt: String, counter: Int, period: Int) : String {
        return if ((counter % period) < period/2) newtxt else txt
    }

    override fun vibrate(r: RectF, vx: Float, vy: Float) {
        TODO("Not yet implemented")
    }

    override fun changeColor(paint: Paint) {
        paint.color = Color.argb(255,255,random.nextInt(255), random.nextInt(255))
    }
}