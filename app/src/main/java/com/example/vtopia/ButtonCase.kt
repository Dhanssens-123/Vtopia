package com.example.vtopia

import android.content.Context
import android.graphics.*

class ButtonCase(x: Float, y: Float, w: Float, h: Float, context: Context, _type: String, _damier: Damier) : Icon(x,y,w,h) {

    private val type = _type
    private val damier = _damier

    private val spriteSet = mapOf<String, Bitmap>(
        "forêt" to BitmapFactory.decodeResource(context.resources, R.drawable.square_green),
        "désert" to BitmapFactory.decodeResource(context.resources, R.drawable.square_yellow),
        "habitat" to BitmapFactory.decodeResource(context.resources, R.drawable.square_brown),
        "culture" to BitmapFactory.decodeResource(context.resources, R.drawable.square_pink),
        "industrie" to BitmapFactory.decodeResource(context.resources, R.drawable.square_grey)
    )

    init{
        changeRectSize(rStroke, STROKE_SIZE,STROKE_SIZE)
    }

    override fun draw(canvas: Canvas?) {

        // Dessine la case
        canvas?.drawBitmap(spriteSet[type]!!, null, r, paintText)
        canvas?.drawRoundRect(rStroke,STROKE_RADIUS,STROKE_RADIUS,paintStroke)
        // Affiche sur le boutton le nombre de cases du même type sur l'île
        paintText.textSize = r.width()/2
        val text = "${damier.getDataSet()[type]}"
        val offSet = paintText.measureText(text)
        canvas?.drawText(text,x-offSet/2,y+paintText.textSize/3,paintText)
    }

    fun getType() : String {
        return type
    }
}