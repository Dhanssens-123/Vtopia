package com.example.vtopia

import android.content.Context
import android.graphics.*

class IconDelta(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icon(_x,_y,_w,_h) {

    private var sprite = BitmapFactory.decodeResource(context.resources, R.drawable.square_bord)
    private val NBRE_BLOC_TOTAL = 10

    private var nbreBloc = 5
    private var lgrBloc = w / NBRE_BLOC_TOTAL

    private var bloc = RectF(x - w/2, y - h/2, x - w/2, y + h/2)

    init {
        changeRectSize(rStroke,STROKE_SIZE,STROKE_SIZE)
    }

    override fun draw(canvas: Canvas?) {

        // Trace le cadre général
        canvas?.drawRoundRect(rStroke,STROKE_RADIUS,STROKE_RADIUS,paintStroke)
        // Trace le bloc de longueur variable et son contour
        var blocStroke = bloc
        bloc.right = r.left + lgrBloc*nbreBloc
        drawBloc(bloc, paint, canvas)
        canvas?.drawRect(blocStroke, paintStroke)
    }

    fun updateBloc(delta: Int) {
        // Transpose un nombre x d'une plage [a,b] à une autre [c,d]
        val map = {x : Int, a: Int, b: Int, c: Int, d: Int -> (x-a)*(d-c)/(b-a) + c}
        // Màj nbreBloc
        nbreBloc = 5 + map(delta, -40, 40, -5, 5)
    }

    fun drawBloc(bloc: RectF, paint: Paint, canvas: Canvas?) {
        // Choisi la couleur du bloc global en fonction de sa taille
        if (nbreBloc < 5) paint.color = Color.argb(255,255,45+(nbreBloc-1)*40,55)
        else paint.color = Color.argb(255, 105+(NBRE_BLOC_TOTAL-nbreBloc)*20, 255, 105)

        canvas?.drawRect(bloc, paint)
    }
}