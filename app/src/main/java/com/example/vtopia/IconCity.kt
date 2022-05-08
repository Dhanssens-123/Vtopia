package com.example.vtopia

import android.content.Context
import android.graphics.*
import java.util.*

class IconCity(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icone(_x,_y,_w,_h), Animation {
    private var random = Random()
    private var band_bmp = flipImageVertically(BitmapFactory.decodeResource(context.resources, R.drawable.band))
    private var tempoy = 100
    private var tempo = 20
    private var vy = random.nextFloat()
    private var switchy = 0

    // Contient le score à afficher sur l'écran, initialement nul et contrôlé par le GameManager
    private var sprite = mutableMapOf<String, Bitmap>(
        "airplane" to BitmapFactory.decodeResource(context.resources, R.drawable.airplane),
        "band" to band_bmp
    )
    private val step = 0.4f
    private val r1 = RectF(r.left, r.top, r.right - step*w, r.bottom)
    private val r2 = RectF((1f-step)*w + r.left, r.top, r.right, r.bottom)
    private var switch = 0
    private var cityName = ""

    override fun draw(canvas: Canvas?) {
        switchy = (switchy + 1)%tempoy
        if (switchy == tempoy/2) {
            vy = -vy
        }
        switch = (switch + 1)%tempo
        if (switch == tempo/2) {
            band_bmp = flipImageHorizontally(band_bmp)
        }
        vibrate(r1,0f,vy)
        canvas?.drawBitmap(band_bmp, null, r1, paint)
        canvas?.drawBitmap(sprite["airplane"]!!, null, r2, paint)
        var flag = 0.75f*w
        var text = "$cityName"
        paint2.textSize = if(1.5F*w/text.length < flag/4F) flag/text.length else flag/6F // Détermination empirique
        paint2.isFakeBoldText = true
        paint2.color = Color.argb(255,52,73,94)
        // Gère l'affichage du score
        var offSet = paint2.measureText(text)
        canvas?.drawText(text,r1.left + w/2 - offSet/2 - step/2*w, r1.top + h/2 + paint2.textSize/3, paint2)
    }

    fun setCityName(name : String) {
        cityName = name
    }

    fun flipImageVertically(bmp : Bitmap) : Bitmap {
        var matrix = Matrix().apply { postScale(-1f, 1f, bmp.width / 2f, bmp.height / 2f) }
        var newbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)
        return newbmp
    }

    fun flipImageHorizontally(bmp : Bitmap) : Bitmap {
        var matrix = Matrix().apply { postScale(1f, -1f, bmp.width / 2f, bmp.height / 2f) }
        var newbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)
        return newbmp
    }

    override fun vibrate(rect: RectF, vx : Float, vy: Float) {
        rect.offset(vx, vy)
    }

    override fun blink(txt: String, newtxt: String, flag: Int): String {
        TODO("Not yet implemented")
    }
}