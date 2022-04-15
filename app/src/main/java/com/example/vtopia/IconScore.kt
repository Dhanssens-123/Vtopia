package com.example.vtopia

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas

class IconScore(_x: Float, _y: Float, _w: Float, _h: Float, context: Context, _type: String) : Icone(_x,_y,_w,_h,context) {
    var type = _type

    val spriteSet = mapOf<String, Bitmap>(
        "therm_in" to BitmapFactory.decodeResource(context.resources, R.drawable.score_therm_in),
        "therm_out" to BitmapFactory.decodeResource(context.resources, R.drawable.score_therm_out),
        "therm_fill" to BitmapFactory.decodeResource(context.resources, R.drawable.score_therm_fill),
    )

    override fun draw(canvas: Canvas?) {
        paint2.color = color
        canvas?.drawBitmap(spriteSet[type]!!, null, r, paint)
    }
}