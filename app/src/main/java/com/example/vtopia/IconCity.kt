package com.example.vtopia

import android.content.Context
import android.graphics.*

class IconCity(_x: Float, _y: Float, _w: Float, _h: Float, context: Context) : Icon(_x,_y,_w,_h), Animable {

    private var band_bmp = flipImageVertically(BitmapFactory.decodeResource(context.resources, R.drawable.band))
    private val airplane_bmp = BitmapFactory.decodeResource(context.resources, R.drawable.airplane)
    private val TEMPO_BAND = 20
    private val TEMPO_AIRPLANE = 100
    private var vy = 0.5f
    private var switch_band = 0
    private var switch_airplane = 0

    // Contient le score à afficher sur l'écran, initialement nul et contrôlé par le GameManager
    private val STEP = 0.4f
    private var cityName = ""
    private val MAX_LENGTH = 10

    override fun draw(canvas: Canvas?) {

        // Itère le compteur et vérifie si un changement de sens est à effectuer
        switch_airplane = (switch_airplane + 1)%TEMPO_AIRPLANE
        if (switch_airplane == TEMPO_AIRPLANE/2) vy = -vy
        vibrate(r, 0f,vy) // Vibre légèrement vers la direction et le sens donné.e
        // Change régulièrement l'orientation de la bande selon son compteur
        switch_band = (switch_band + 1)%TEMPO_BAND
        if (switch_band == TEMPO_BAND/2) band_bmp = flipImageHorizontally(band_bmp)
        // Dessine l'avion et la bande
        val r1 = RectF(r.left, r.top, r.right - STEP*w, r.bottom)
        val r2 = RectF((1f-STEP)*w + r.left, r.top, r.right, r.bottom)
        canvas?.drawBitmap(band_bmp, null, r1, paint)
        canvas?.drawBitmap(airplane_bmp, null, r2, paint)
        // Affiche le nom de la ville sur la bande
        var text = "$cityName".toUpperCase()
        paintText.textSize = r1.width()/MAX_LENGTH * (1 + 0.1f*(MAX_LENGTH - text.length))
        var offSet = paintText.measureText(text)
        canvas?.drawText(text,r1.left + w/2 - offSet/2 - STEP/2*w - r1.width()/20, r1.top + h/2 + paintText.textSize/3, paintText)
    }

    fun setCityName(name : String) {
        cityName = name
    }

    fun flipImageVertically(bmp : Bitmap) : Bitmap {
        // Retourne verticalement l'image
        var matrix = Matrix().apply { postScale(-1f, 1f, bmp.width / 2f, bmp.height / 2f) }
        var newbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)
        return newbmp
    }

    fun flipImageHorizontally(bmp : Bitmap) : Bitmap {
        // Retourne horizontalement l'image
        var matrix = Matrix().apply { postScale(1f, -1f, bmp.width / 2f, bmp.height / 2f) }
        var newbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)
        return newbmp
    }

    override fun vibrate(rect: RectF, vx : Float, vy: Float) {
        rect.offset(vx, vy)
    }

    override fun blink(txt: String, newtxt: String, counter: Int, period: Int): String {
        TODO("Not yet implemented")
    }

    override fun changeColor(paint: Paint) {
        TODO("Not yet implemented")
    }
}