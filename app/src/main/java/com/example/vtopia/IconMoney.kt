package com.example.vtopia

import android.content.Context
import android.graphics.*

class IconMoney(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icon(_x,_y,_w,_h) {

    private var sprite = BitmapFactory.decodeResource(context.resources, R.drawable.square_yellow)
    private val NBRE_BLOC_TOTAL = 10

    private var nbreBloc = 5
    private var lgrBloc = w / NBRE_BLOC_TOTAL

    init {
        changeRectSize(rStroke, STROKE_SIZE,STROKE_SIZE)
    }

    override fun draw(canvas: Canvas?) {

        canvas?.drawRoundRect(rStroke,10f,10f,paintStroke)

        var bloc = RectF(x - w/2, y - h/2, x - w/2 + lgrBloc, y + h/2)
        for (i in 0 until nbreBloc) {
            canvas?.drawBitmap(sprite,null, bloc, paint)
            bloc.left += lgrBloc
            bloc.right += lgrBloc
        }
    }

    fun updateBloc(a: Int) {
        nbreBloc += a
        if (nbreBloc >= NBRE_BLOC_TOTAL) nbreBloc = 10
    }

    fun getNbreBloc() : Int {
        return nbreBloc
    }
}