package com.example.vtopia

import android.content.Context
import android.graphics.*

class Delta(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icone(_x,_y,_w,_h) {
    private var sprite1 = BitmapFactory.decodeResource(context.resources, R.drawable.square_corail)
    private var sprite2 = BitmapFactory.decodeResource(context.resources, R.drawable.square_green)
    private val NBRE_BLOC_TOTAL = 10

    private var nbreBloc = 5
    private var lgrBloc = w / NBRE_BLOC_TOTAL
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(sprite1,null, r, paint)
        var bloc = RectF(x - w/2, y - h/2, x - w/2 + lgrBloc, y + h/2)
        for (i in 0 until nbreBloc) {
            drawBloc(bloc, paint, canvas)
            bloc.left += lgrBloc
            bloc.right += lgrBloc
        }
    }

    fun updateBloc(delta: Int) {
        val map = {x : Int, a: Int, b: Int, c: Int, d: Int -> (x-a)*(d-c)/(b-a) + c} // Transpose un nombre x d'une plage [a,b] à une autre [c,d]
        nbreBloc = 5 + map(delta, -50, 50, -5, 5)
    }

    fun drawBloc(bloc: RectF, paint: Paint, canvas: Canvas?) {
        if (nbreBloc < 5) paint.color = Color.argb(255,nbreBloc*255/5,0,0)
        else paint.color = Color.argb(255, 0, nbreBloc*255/10, 0)
        canvas?.drawRect(bloc, paint)
    }
}