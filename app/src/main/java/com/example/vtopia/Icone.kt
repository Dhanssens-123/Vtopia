package com.example.vtopia

import android.content.Context
import android.graphics.*
import androidx.core.graphics.toRect

abstract class Icone (_x: Float, _y: Float, _w: Float, _h: Float) {
    /*
    Contient les paramètres de base pour l'affichage via l'appel draw() du canvas de toutes les icônes de la GameView.
    Une classe abstraite ne peut pas être instanciée.
    */
    protected var x = _x
    protected var y = _y
    protected var w = _w
    protected var h = _h

    protected var r = RectF(x - w/2, y - h/2, x + w/2, y + h/2)
    protected val paint = Paint()

    protected val STROKE_SIZE = r.height()/10
    protected var rStroke = r
    protected var paintStroke = Paint().apply {
        color = Color.argb(255,52,73,94)
        style = Paint.Style.STROKE
        strokeWidth = STROKE_SIZE
    }

    protected val paintText = Paint().apply {
        isFakeBoldText = true
        color = Color.argb(255,52,73,94)
    }

    abstract fun draw(canvas: Canvas?) // Une méthode abstraite possède une signature mais pas de corps

    fun changeRectSize(rect: RectF, vx: Float, vy: Float) {
        rect.left -= vx/2
        rect.right += vx/2
        rect.top -= vy/2
        rect.bottom += vy/2
    }

    fun reinitRectSize(rect: RectF) {
        rect.left = x - w/2
        rect.top = y - h/2
        rect.right = x + w/2
        rect.bottom = y + h/2
    }

    fun getRect() : RectF {
        return r
    }

    fun getRectStroke() : RectF {
        return rStroke
    }

    fun getStrokeSize() : Float {
        return STROKE_SIZE
    }
}