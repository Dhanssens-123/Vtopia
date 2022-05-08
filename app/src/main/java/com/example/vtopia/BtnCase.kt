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
        "industrie" to BitmapFactory.decodeResource(context.resources, R.drawable.square_grey),
        "bord" to BitmapFactory.decodeResource(context.resources, R.drawable.square_bord)
    )

    override fun draw(canvas: Canvas?) {
        paint2.textSize = w/2
        paint2.isFakeBoldText = true
        paint2.color = Color.argb(255,52,73,94)
        var text = "${damier.getDataSet()[type]}"
        var offSet = paint2.measureText(text)
        canvas?.drawBitmap(spriteSet[type]!!, null, r, paint)
        canvas?.drawBitmap(spriteSet["bord"]!!, null, r, paint)
        canvas?.drawText(text,x-offSet/2,y+paint2.textSize/3,paint2)
    }

    fun changeRect(a : Int) {
        var add = a.toFloat()
        var w2 = w + add
        var h2 = h + add
        r = RectF(x - w2 / 2, y - h2 / 2, x + w2 / 2, y + h2 / 2)
    }

    fun reinit() {
        r = RectF(x - w/2, y - h/2, x + w/2, y + h/2)
    }

    fun getType() : String {
        return type
    }
}