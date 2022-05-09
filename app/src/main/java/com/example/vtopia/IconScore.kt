package com.example.vtopia

import android.graphics.*

class IconScore(x: Float, y: Float, w: Float, h: Float) : Icon(x,y,w,h), Animable {

    // Contient le score à afficher sur l'écran, initialement nul et contrôlé par le GameManager
    private var score = 0
    private var counter = 0
    private val BLINK_PERIOD = 10 // Divisible par 2

    fun changeScore(newscore : Int) {
        score = newscore
    }

    override fun draw(canvas: Canvas?) {
        var text = "$score"

        if (score < 0) {
            text = blink(text,"", counter, BLINK_PERIOD)
            counter += 1
        } else counter = 0

        // Gère l'affichage du score
        var length = paintText.measureText(text)
        var offSet = if (text.length > 2) (2*text.length-2)/(2*text.length).toFloat() else 0.5F
        canvas?.drawText(text,x - offSet*length, y + paintText.textSize/3, paintText)
    }

    override  fun blink(txt: String, newtxt: String, counter: Int, period: Int) : String {
        return if ((counter % period) < period/2) newtxt else txt
    }

    override fun vibrate(r: RectF, vx: Float, vy: Float) {
        TODO("Not yet implemented")
    }

    override fun changeColor(paint: Paint) {
        TODO("Not yet implemented")
    }
}