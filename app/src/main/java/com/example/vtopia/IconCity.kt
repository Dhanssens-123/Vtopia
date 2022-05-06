package com.example.vtopia

import android.content.Context
import android.graphics.*

class IconCity(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icone(_x,_y,_w,_h,context), Animation {

    // Contient le score à afficher sur l'écran, initialement nul et contrôlé par le GameManager
    private val sprite = mapOf<String, Bitmap>(
        "yellow" to BitmapFactory.decodeResource(context.resources, R.drawable.square_yellow),
        "blue" to BitmapFactory.decodeResource(context.resources, R.drawable.square_blue)
    )
    private val step = 0.3f
    private val r1 = RectF(r.left, r.top, r.right - step*w, r.bottom)
    private val r2 = RectF((1.1f-step)*w + r.left, r.top, r.right, r.bottom)
    private var switch = 0
    private var cityName = ""

    override fun draw(canvas: Canvas?) {
        var sprite_string = "yellow"
        sprite_string = blink("yellow","blue",switch)
        switch = if (switch < 100) switch + 1 else 0
        canvas?.drawBitmap(sprite[sprite_string]!!, null, r1, paint)
        canvas?.drawBitmap(sprite["blue"]!!, null, r2, paint)
        var flag = 0.75f*w
        var text = "$cityName"
        paint2.textSize = if(1.5F*w/text.length < flag/4F) 1.5F*flag/text.length else flag/4F // Détermination empirique
        paint2.isFakeBoldText = true
        paint2.color = Color.argb(255,52,73,94)
        // Gère l'affichage du score
        var offSet = paint2.measureText(text)
        canvas?.drawText(text,x - offSet/2 - step/2*w, y + paint2.textSize/3, paint2)
    }

    fun setCityName(name : String) {
        cityName = name
    }
}