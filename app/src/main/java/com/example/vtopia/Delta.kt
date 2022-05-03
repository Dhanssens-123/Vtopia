package com.example.vtopia

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF

class Delta(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icone(_x,_y,_w,_h,context) {
    var sprite1 = BitmapFactory.decodeResource(context.resources, R.drawable.square_corail)
    var sprite2 = BitmapFactory.decodeResource(context.resources, R.drawable.square_green)
    val NBRE_BLOC_TOTAL = 10

    var nbreBloc = 5
    var lgrBloc = w / NBRE_BLOC_TOTAL
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(sprite1,null, r, paint)
        var bloc = RectF(x - w/2, y - h/2, x - w/2 + lgrBloc, y + h/2)
        for (i in 0 until nbreBloc) {
            canvas?.drawBitmap(sprite2,null, bloc, paint)
            bloc.left += lgrBloc
            bloc.right += lgrBloc
        }
    }

    fun updateBloc(delta: Int) {
        val map = {x : Int, a: Int, b: Int, c: Int, d: Int -> (x-a)*(d-c)/(b-a) + c} // Transpose un nombre x d'une plage [a,b] à une autre [c,d]
        nbreBloc = 5 + map(delta, -50, 50, -5, 5)
    }
}