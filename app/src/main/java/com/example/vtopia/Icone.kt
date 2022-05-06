package com.example.vtopia

import android.content.Context
import android.graphics.*

abstract class Icone (_x: Float, _y: Float, _w: Float, _h: Float, context: Context) {
    /*
    Contient les paramètres de base pour l'affichage via l'appel draw() du canvas de toutes les icônes de la GameView
    */
    protected var x = _x
    protected var y = _y
    protected var w = _w
    protected var h = _h
    protected var r = RectF(x - w/2, y - h/2, x + w/2, y + h/2)
    protected val paint = Paint()
    protected var paint2 = Paint()

    abstract fun draw(canvas: Canvas?)

    fun getRect() : RectF {
        return r
    }
}