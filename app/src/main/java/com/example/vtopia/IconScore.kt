package com.example.vtopia

import android.graphics.*

class IconScore(x: Float, y: Float, w: Float, h: Float) : Icon(x,y,w,h), Animable {

    private var score = 0
    private var counter = 0
    private val BLINK_PERIOD = 20 // Divisible par 2 !

    fun changeScore(newscore : Int) {
        score = newscore
    }

    override fun draw(canvas: Canvas?) {
        var text = "$score"

        // Si le score est négatif, met à jour le compteur et clignote en fonction de ce dernier
        if (score < 0) {
            text = blink(text,"", counter, BLINK_PERIOD)
            counter += 1
        } else counter = 0

        // Affiche le score
        var length = paintText.measureText(text)
        // Décalage de l'emplacement pour une meilleure esthétique
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