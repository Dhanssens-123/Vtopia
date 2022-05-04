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

class EasterEggView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), Runnable {

    val backgroundPaint = Paint()

    // Prend les dimensions de la drawingView ( != dimensions de l'écran total)
    private val displayMetrics = DisplayMetrics()
    private var screenWidth = context.resources.displayMetrics.widthPixels.toFloat()
    private var screenHeight = context.resources.displayMetrics.heightPixels.toFloat()

    var airplane = AirPlane(screenWidth/2, screenHeight/2, 50F)
    var lesParois = arrayOf(
        Parois(5f, 5f, 25f, screenHeight),
        Parois(5f, 5f, screenWidth, 25f),
        Parois(5f, screenHeight, screenWidth, screenHeight + 25),
        Parois(screenWidth, 5f, screenWidth + 25, screenHeight + 25)
    )

    var drawing: Boolean = true
    lateinit var canvas: Canvas
    lateinit var thread: Thread

    init {
        backgroundPaint.color = Color.argb(255,93,173,226)
    }


    override fun run() {
        while (drawing) {
            airplane.bouge(lesParois)
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
            canvas.drawRect(0F, 0F, canvas.getWidth()*1F, canvas.getHeight()*1F, backgroundPaint) // Fond d'écran
            airplane.draw(canvas)
            for (p in lesParois) p.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }
}