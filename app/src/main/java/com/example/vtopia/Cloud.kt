package com.example.vtopia

import android.content.Context
import android.graphics.*
import java.util.*

class Cloud (x: Float, y: Float, _diametre : Float, context: Context) : Aerial(x,y,_diametre, context), Animation {
    var color = Color.argb(255,random.nextInt(255),255,255)
    var tempox = 100 + random.nextInt(200)
    var tempoy = 100 + random.nextInt(200)
    var vx = random.nextFloat()
    var vy = random.nextFloat()
    var switchx = 0
    var switchy = 0

    var sprite = BitmapFactory.decodeResource(context.resources, R.drawable.cloud)

    override fun vibrate(vx : Float, vy: Float) {
        this.r.offset(vx, vy)
    }

    override fun draw(canvas: Canvas?) {
        switchx = (switchx + 1)%tempox
        switchy = (switchx + 1)%tempoy
        if (switchx == tempox/2) {
            vx = -vx
        }
        if (switchy == tempoy/2) {
            vy = -vy
        }
        vibrate(vx,vy)
        canvas?.drawBitmap(sprite, null, r, paint)
        paint.color = color
    }
}