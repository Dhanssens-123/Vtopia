package com.example.vtopia

import android.content.Context
import android.graphics.*

class BtnCase(x: Float, y: Float, w: Float, h: Float, context: Context, _type: String, _damier: Damier) : Icone(x,y,w,h) {

    private val type = _type
    private val damier = _damier
    private var isClicked = false

    val spriteSet = mapOf<String, Bitmap>(
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

        paintText.textSize = r.width()/2
        var text = "${damier.getDataSet()[type]}"
        var offSet = paintText.measureText(text)

        canvas?.drawBitmap(spriteSet[type]!!, null, r, paintText)
        canvas?.drawRoundRect(rStroke,10f,10f,paintStroke)

        canvas?.drawText(text,x-offSet/2,y+paintText.textSize/3,paintText)
    }

    fun getType() : String {
        return type
    }
}