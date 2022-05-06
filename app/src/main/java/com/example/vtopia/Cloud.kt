package com.example.vtopia

import android.content.Context
import android.graphics.*
import java.util.*

class Cloud (x: Float, y: Float, w : Float, h : Float, context: Context) : Aerial(x,y, w, h, context), Animation {
    var tempox = 200 + random.nextInt(200)
    var tempoy = 200 + random.nextInt(200)
    var vx = random.nextFloat()*1.5f
    var vy = random.nextFloat()*1.5f
    var switchx = 0
    var switchy = 0

    var sprite = BitmapFactory.decodeResource(context.resources, R.drawable.cloud)

    override fun vibrate(vx : Float, vy: Float) {
        this.r.offset(vx, vy)
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
        vibrate(vx,vy)
        canvas?.drawBitmap(sprite, null, r, paint)
    }
}