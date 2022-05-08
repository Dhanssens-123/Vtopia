package com.example.vtopia

import android.content.Context
import android.graphics.*
import android.widget.Toast
import kotlin.coroutines.coroutineContext

class Money(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icone(_x,_y,_w,_h) {

    private var sprite1 = BitmapFactory.decodeResource(context.resources, R.drawable.square_corail)
    private var sprite2 = BitmapFactory.decodeResource(context.resources, R.drawable.square_yellow)
    private val NBRE_BLOC_TOTAL = 10

    private var nbreBloc = 5
    private var lgrBloc = w / NBRE_BLOC_TOTAL

    init {
        changeRectSize(rStroke, 10f,10f)
        paintStroke.color = Color.argb(255,52,73,94)
        paintStroke.style = Paint.Style.STROKE
        paintStroke.strokeWidth = 20f
    }

    override fun draw(canvas: Canvas?) {

        canvas?.drawRoundRect(rStroke,10f,10f,paintStroke)

        var bloc = RectF(x - w/2, y - h/2, x - w/2 + lgrBloc, y + h/2)
        for (i in 0 until nbreBloc) {
            canvas?.drawBitmap(sprite2,null, bloc, paint)
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