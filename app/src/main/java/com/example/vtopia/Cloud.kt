package com.example.vtopia

import android.content.Context
import android.graphics.*

class Cloud (x: Float, y: Float, w : Float, h : Float, context: Context) : Aerial(x,y, w, h), Animable {

    private var color = Color.argb(255,random.nextInt(255),255,255)
    private var tempox = 200 + random.nextInt(200)
    private var tempoy = 200 + random.nextInt(200)
    private var vx = random.nextFloat()*1.5f
    private var vy = random.nextFloat()*1.5f
    private var counterx = 0
    private var countery = 0
    private val COLOR_PERIOD = 100

    private var sprite = BitmapFactory.decodeResource(context.resources, R.drawable.cloud).extractAlpha()

    init{
        paint.color = color
    }

    override fun draw(canvas: Canvas?) {

        // Itère les 2 compteurs et vérifie si un changement de sens est à effectuer
        counterx = (counterx + 1)%tempox
        countery = (countery + 1)%tempoy
        if (counterx == tempox/2) vx = -vx
        if (countery == tempoy/2) vy = -vy
        vibrate(r,vx,vy) // Vibre légèrement vers la direction et le sens donné.e
        // Change régulièrement la couleur selon le compteur de la direction horizontale
        if (counterx % COLOR_PERIOD == COLOR_PERIOD/2) changeColor(paint)
        // Dessine le nuage
        canvas?.drawBitmap(sprite, null, r, paint)
    }

    override fun vibrate(rect : RectF, vx : Float, vy: Float) {
        rect.offset(vx, vy)
    }

    override fun blink(txt: String, newtxt: String, counter: Int, period: Int): String {
        TODO("Not yet implemented")
    }

    override fun changeColor(paint: Paint) {
        paint.color = Color.argb(255,100+random.nextInt(155),255,255)
    }
}