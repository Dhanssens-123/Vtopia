package com.example.vtopia

import android.content.Context
import android.graphics.*
import java.util.*

class Cloud (x: Float, y: Float, w : Float, h : Float, context: Context) : Aerial(x,y, w, h), Animation {
    private var tempox = 200 + random.nextInt(200)
    private var tempoy = 200 + random.nextInt(200)
    private var vx = random.nextFloat()*1.5f
    private var vy = random.nextFloat()*1.5f
    private var switchx = 0
    private var switchy = 0

    private var sprite = BitmapFactory.decodeResource(context.resources, R.drawable.cloud)

    override fun vibrate(rect : RectF, vx : Float, vy: Float) {
        rect.offset(vx, vy)
    }

    override fun draw(canvas: Canvas?) {
        switchx = (switchx + 1)%tempox
        switchy = (switchy + 1)%tempoy
        if (switchx == tempox/2) {
            vx = -vx
        }
        if (switchy == tempoy/2) {
            vy = -vy
        }
        vibrate(r,vx,vy)
        canvas?.drawBitmap(sprite, null, r, paint)
    }
}