package com.example.vtopia

import android.content.Context
import android.graphics.*

class Delta(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icone(_x,_y,_w,_h) {
    private var sprite = BitmapFactory.decodeResource(context.resources, R.drawable.square_bord)
    private val NBRE_BLOC_TOTAL = 10

    private var nbreBloc = 5
    private var lgrBloc = w / NBRE_BLOC_TOTAL

    init {
        changeRectSize(rStroke,10f,10f)
        paintStroke.color = Color.argb(255,52,73,94)
        paintStroke.style = Paint.Style.STROKE
        paintStroke.strokeWidth = 20f
    }

    override fun draw(canvas: Canvas?) {

        canvas?.drawRoundRect(rStroke,10f,10f,paintStroke)

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
        if (nbreBloc < 5) paint.color = Color.argb(255,255,45+(nbreBloc-1)*40,55)
        else paint.color = Color.argb(255, 105+(NBRE_BLOC_TOTAL-nbreBloc)*20, 255, 105)
        canvas?.drawRect(bloc, paint)
    }
}