package com.example.vtopia

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color

class BtnCase(_x: Float, _y: Float, _w: Float, _h: Float, context: Context, _type: String, _damier: Damier) : Icone(_x,_y,_w,_h,context) {

    val type = _type
    val damier = _damier

    val spriteSet = mapOf<String, Bitmap>(
        "forêt" to BitmapFactory.decodeResource(context.resources, R.drawable.square_green),
        "désert" to BitmapFactory.decodeResource(context.resources, R.drawable.square_yellow),
        "lac" to BitmapFactory.decodeResource(context.resources, R.drawable.square_blue),
        "habitat" to BitmapFactory.decodeResource(context.resources, R.drawable.square_brown),
        "culture" to BitmapFactory.decodeResource(context.resources, R.drawable.square_pink),
        "extraction" to BitmapFactory.decodeResource(context.resources, R.drawable.square_corail),
        "industrie" to BitmapFactory.decodeResource(context.resources, R.drawable.square_grey),
        "bord" to BitmapFactory.decodeResource(context.resources, R.drawable.square_bord)
    )

    override fun draw(canvas: Canvas?) {
        paint2.textSize = w/2
        paint2.isFakeBoldText = true
        paint2.color = Color.argb(255,52,73,94)
        var text = "${damier.dataSet[type]}"
        var offSet = paint2.measureText(text)
        canvas?.drawBitmap(spriteSet[type]!!, null, r, paint)
        canvas?.drawBitmap(spriteSet["bord"]!!, null, r, paint)
        canvas?.drawText(text,x-offSet/2,y+offSet/3,paint2)
    }
}