package com.example.vtopia

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class IconTime(_x: Float, _y: Float, _w: Float, _h: Float) : Icon(_x,_y,_w,_h), Animable {

    // Contient le timer à afficher sur l'écran contrôlé par le GameManager
    private var time = 0.0
    private var counter = 0
    private var BLINK_PERIOD = 10 // Divisible par 2
    private var MULTICOLOR_PERIOD = 10 // Divisible par 2

    fun changeTime(newTime: Double) {
        time = newTime
    }

    override fun draw(canvas: Canvas?) {
        var formatted_time = String.format("%d", time.toInt())

        // Si le timer se trouve à une certaine valeur,
        // met à jour le compteur et clignote en fonction de ce dernier
        if (time.toInt() % 10 == 0 || time.toInt() < 5) {
            formatted_time = blink(formatted_time, "", counter, BLINK_PERIOD)
            counter += 1
        } else counter = 0
        // S'il reste moins de 3sec,
        // change régulièrement la couleur selon le compteur
        if (time.toInt() <= 3) {
            paintText.textSize = 1.5f*w
            if (counter%MULTICOLOR_PERIOD == MULTICOLOR_PERIOD/2) changeColor(paintText)
        }

        // Affiche le temps restant
        val offSet = paintText.measureText(formatted_time)
        canvas?.drawText(formatted_time, x - offSet/2, y + paintText.textSize/3, paintText)
    }

    fun reset() {
        paintText.color = color_dark
        paintText.textSize = r.width()
    }

    override  fun blink(txt: String, newtxt: String, counter: Int, period: Int) : String {
        return if ((counter % period) < period/2) newtxt else txt
    }

    override fun vibrate(rect: RectF, vx: Float, vy: Float) {
        TODO("Not yet implemented")
    }

    override fun changeColor(paint: Paint) {
        paint.color = Color.argb(255,255,random.nextInt(255), random.nextInt(255))
    }
}