package com.example.vtopia

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.SurfaceView
import java.util.*

class EasterEggView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), Runnable {

    private val backgroundPaint = Paint()
    private var random = Random()

    // Récupère les dimensions de la drawingView ( != dimensions de l'écran total)
    private var w = context.resources.displayMetrics.widthPixels.toFloat()
    private var h = context.resources.displayMetrics.heightPixels.toFloat()

    // Création des différents éléments de la SurfaceView
    private var airplane = AirPlane(w/2, h/2, w/5, w/5, context)
    private var clouds = ArrayList<Cloud>(5)
    private var snake = Snake(airplane)
    private var lesParois = arrayOf(
        Parois(0f, 0f, 25f, h),
        Parois(0f, 0f, w, 25f),
        Parois(0f, h - 25f, w, h),
        Parois(w - 25f, 0f, w, h)
    )
    private val NBRE_CLOUDS = 20

    private var drawing: Boolean = true
    lateinit private var canvas: Canvas
    lateinit private var thread: Thread

    init {
        backgroundPaint.color = Color.argb(255,93,173,226)
        for (i in 1..NBRE_CLOUDS) clouds.add(Cloud(w/20 + random.nextFloat()*9/10*w,
            w/20 + random.nextFloat()*9/10*h,w/6, w/12, context))
    }

    override fun run() {
        while (drawing) {
            airplane.bouge(lesParois)
            snake.update(airplane)
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
            canvas.drawRect(0F, 0F, w*1f, h*1f, backgroundPaint) // Fond d'écran
            for (cloud in clouds) {
                // Si l'avion percute l'avion, le nuage est transféré dans la chaine Snake
                if (RectF.intersects(airplane.getRect(), cloud.getRect())) {
                    snake.ellongate(cloud)
                    clouds.remove(cloud)
                    break
                }
            }
            for (elem in snake.getSnake()) elem.draw(canvas)
            for (cloud in clouds) {
                cloud.draw(canvas)
            }
            holder.unlockCanvasAndPost(canvas)
        }
    }
}