package com.example.vtopia

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.widget.Toast
import kotlin.coroutines.coroutineContext

class Money(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icone(_x,_y,_w,_h,context) {

    private var sprite1 = BitmapFactory.decodeResource(context.resources, R.drawable.square_corail)
    private var sprite2 = BitmapFactory.decodeResource(context.resources, R.drawable.square_yellow)
    private val NBRE_BLOC_TOTAL = 10

    private var nbreBloc = 5
    private var lgrBloc = w / NBRE_BLOC_TOTAL

    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(sprite1,null, r, paint)
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