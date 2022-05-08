package com.example.vtopia

import android.content.Context
import android.graphics.*

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

    protected var rStroke = r
    protected var paintStroke = Paint()

    abstract fun draw(canvas: Canvas?) // Une méthode abstraite possède une signature mais pas de corps

    fun changeRectSize(r: RectF, vx: Float, vy: Float) {
        r.left -= vx
        r.right += vx
        r.top -= vy
        r.bottom += vy
    }

    fun getRect() : RectF {
        return r
    }
}