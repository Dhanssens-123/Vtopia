package com.example.vtopia

import android.content.Context
import android.graphics.*

class IconScore(x: Float, y: Float, w: Float, h: Float) : Icone(x,y,w,h), Animable {

    // Contient le score à afficher sur l'écran, initialement nul et contrôlé par le GameManager
    private var score = 0
    private var switch = 0

    init {
        paint.textSize = w
        paint.isFakeBoldText = true
        paint.color = Color.argb(255,52,73,94)
    }

    fun changeScore(newscore : Int) {
        score = newscore
    }

    override fun draw(canvas: Canvas?) {
        var text = "$score"

        if (score < 0) {
            text = blink(text,"", switch)
            switch += 1
        } else switch = 0

        // Gère l'affichage du score
        var length = paint.measureText(text)
        var offSet = if (text.length > 2) (2*text.length-2)/(2*text.length).toFloat() else 0.5F
        canvas?.drawText(text,x - offSet*length, y + paint.textSize/3, paint)
    }

    override  fun blink(txt: String, newtxt: String, flag: Int) : String {
        return if ((flag % 20) < 10) newtxt else txt
    }

    override fun vibrate(r: RectF, vx: Float, vy: Float) {
        TODO("Not yet implemented")
    }
}