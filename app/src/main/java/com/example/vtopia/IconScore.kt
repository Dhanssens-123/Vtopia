package com.example.vtopia

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color

class IconScore(_x: Float, _y: Float, _w: Float, _h: Float, context: Context, _type: String) : Icone(_x,_y,_w,_h,context) {
    var type = _type
    var score = 0

    val spriteSet = mapOf<String, Bitmap>(
        "therm_in" to BitmapFactory.decodeResource(context.resources, R.drawable.score_therm_in),
        "therm_out" to BitmapFactory.decodeResource(context.resources, R.drawable.score_therm_out),
        "therm_fill" to BitmapFactory.decodeResource(context.resources, R.drawable.score_therm_fill),
    )

    override fun draw(canvas: Canvas?) {
        paint2.textSize = w/8
        paint2.isFakeBoldText = false
        paint2.color = Color.argb(255,52,73,94)
        var text = "$score"
        var offSet = paint.measureText(text)
        canvas?.drawBitmap(spriteSet[type]!!, null, r, paint)
        canvas?.drawText(text,x - offSet/2, y + paint2.textSize/3, paint2)
    }
}