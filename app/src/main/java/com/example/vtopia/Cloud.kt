package com.example.vtopia

import android.content.Context
import android.graphics.*
import java.util.*

class Cloud (x: Float, y: Float, w : Float, h : Float, context: Context) : Aerial(x,y, w, h), Animable {

    private var color = Color.argb(255,random.nextInt(255),255,255)
    private var tempox = 200 + random.nextInt(200)
    private var tempoy = 200 + random.nextInt(200)
    private var vx = random.nextFloat()*1.5f
    private var vy = random.nextFloat()*1.5f
    private var counterx = 0
    private var countery = 0

    private var sprite = BitmapFactory.decodeResource(context.resources, R.drawable.cloud).extractAlpha()

    override fun draw(canvas: Canvas?) {
        counterx = (counterx + 1)%tempox
        countery = (countery + 1)%tempoy
        if (counterx == tempox/2) vx = -vx
        if (countery == tempoy/2) vy = -vy
        vibrate(r,vx,vy)

        paint.color = color
        canvas?.drawBitmap(sprite, null, r, paint)
    }

    override fun vibrate(rect : RectF, vx : Float, vy: Float) {
        rect.offset(vx, vy)
    }

    override fun blink(txt: String, newtxt: String, counter: Int, period: Int): String {
        TODO("Not yet implemented")
    }

    override fun changeColor(paint: Paint) {
        TODO("Not yet implemented")
    }
}