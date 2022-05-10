package com.example.vtopia

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*

class RulesView  @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), Runnable {

    private val backgroundPaint = Paint()
    private val textPaint = Paint()
    private var random = Random()

    private var drawing: Boolean = true
    lateinit private var canvas: Canvas
    lateinit private var thread: Thread

    // Récupère les dimensions de la drawingView ( != dimensions de l'écran total)
    private val displayMetrics = DisplayMetrics()
    private var w = context.resources.displayMetrics.widthPixels.toFloat()
    private var h = context.resources.displayMetrics.heightPixels.toFloat()

    // Création des différents éléments de la SurfaceView
    private var clouds = ArrayList<Cloud>()
    private var iconCity = IconCity(w/2,h/10,w/2,h/10, context)
    private var NBRE_CLOUDS = 20

    init {
        backgroundPaint.color = Color.argb(255,93,173,226)
        for (i in 1..NBRE_CLOUDS) clouds.add(Cloud(w/20 + random.nextFloat()*9/10*w,
            w/20 + random.nextFloat()*9/10*h,w/6, w/12, context))
        iconCity.setCityName("VTOPIA")
    }

    override fun run() {
        while (drawing) {
            draw()
        }
    }

    fun pause() {
        drawing = false
        thread.join()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0F, 0F, w,
                h, backgroundPaint)
            for (cloud in clouds) {
                cloud.draw(canvas)
            }
            iconCity.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }
}