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

class TipsView  @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), Runnable {

    val backgroundPaint = Paint()
    val textPaint = Paint()
    private var random = Random()

    var drawing: Boolean = true
    lateinit var canvas: Canvas
    lateinit var thread: Thread

    // Prend les dimensions de la drawingView ( != dimensions de l'écran total)
    private val displayMetrics = DisplayMetrics()
    private var screenWidth = context.resources.displayMetrics.widthPixels.toFloat()
    private var screenHeight = context.resources.displayMetrics.heightPixels.toFloat()

    private var clouds = ArrayList<Cloud>(5)
    private var iconCity = IconCity(screenWidth/2,150F,600F,200F, context)

    init {
        backgroundPaint.color = Color.argb(255,93,173,226)
        for (i in 1..20) clouds.add(Cloud(random.nextFloat()*screenWidth*0.95f, random.nextFloat()*screenHeight*0.95f,200f, 100f, context))
        iconCity.setCityName("")
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
            canvas.drawRect(0F, 0F, canvas.getWidth()*1F,
                canvas.getHeight()*1F, backgroundPaint)
            for (cloud in clouds) {
                cloud.draw(canvas)
            }
            iconCity.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }
}