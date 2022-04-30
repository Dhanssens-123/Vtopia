package com.example.vtopia

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.widget.Toast
import kotlin.coroutines.coroutineContext

class Money(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icone(_x,_y,_w,_h,context) {

    var sprite1 = BitmapFactory.decodeResource(context.resources, R.drawable.square_corail)
    var sprite2 = BitmapFactory.decodeResource(context.resources, R.drawable.square_yellow)
    val NBRE_BLOC_TOTAL = 10

    var nbreBloc = 5
    var lgrBloc = w / NBRE_BLOC_TOTAL
    var cityName = "cityName"

    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(sprite1,null, r, paint)
        var bloc = RectF(x - w/2, y - h/2, x - w/2 + lgrBloc, y + h/2)
        for (i in 0 until nbreBloc) {
            canvas?.drawBitmap(sprite2,null, bloc, paint)
            bloc.left += lgrBloc
            bloc.right += lgrBloc
        }

        paint2.textSize = w/8
        paint2.isFakeBoldText = false
        paint2.color = Color.argb(255,52,73,94)
        // Gère l'affichage du score
        var text = "$cityName"
        var offSet = paint.measureText(text)
        canvas?.drawText(text,x - offSet/2, y + paint2.textSize/3, paint2)
    }

    fun updateBloc(a: Int) {
        nbreBloc += a
        if (nbreBloc >= NBRE_BLOC_TOTAL) nbreBloc = 10
    }
}