package com.example.vtopia

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.TypedValue

class IconTime(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icone(_x,_y,_w,_h,context) {
    override fun draw(canvas: Canvas?) {
        paint.textSize = w
        paint.isFakeBoldText = true
        paint.color = Color.argb(255,52,73,94)
        var text = "10 sec left"
        var offSet = paint.measureText(text)
        canvas?.drawText(text, x - offSet/2, y, paint)
    }
}