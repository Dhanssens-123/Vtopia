package com.example.vtopia

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*

class Cloud (x: Float, y: Float, _diametre : Float) : Aerial(x,y,_diametre), Animation {
    var color = Color.argb(255,random.nextInt(255),255,255)
    var tempox = 100 + random.nextInt(200)
    var tempoy = 100 + random.nextInt(200)
    var vx = random.nextFloat()
    var vy = random.nextFloat()
    var switchx = 0
    var switchy = 0

    override fun draw(canvas: Canvas?) {
        switchx = (switchx + 1)%tempox
        switchy = (switchx + 1)%tempoy
        if (switchx == tempox/2) {
            vx = -vx
        }
        if (switchy == tempoy/2) {
            vy = -vy
        }
        vibrate(this, vx,vy)
        canvas?.drawOval(r, paint)
        paint.color = color
    }
}