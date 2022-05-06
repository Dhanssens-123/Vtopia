package com.example.vtopia

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*

class EasterEggView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), Runnable {

    private val backgroundPaint = Paint()
    private var random = Random()

    // Prend les dimensions de la drawingView ( != dimensions de l'écran total)
    private val displayMetrics = DisplayMetrics()
    private var screenWidth = context.resources.displayMetrics.widthPixels.toFloat()
    private var screenHeight = context.resources.displayMetrics.heightPixels.toFloat()

    private var airplane = AirPlane(screenWidth/2, screenHeight/2, 200f, 220f, context)
    private var clouds = ArrayList<Cloud>(5)
    private var snake = Snake(airplane)
    private var lesParois = arrayOf(
        Parois(0f, 0f, 25f, screenHeight),
        Parois(0f, 0f, screenWidth, 25f),
        Parois(0f, screenHeight - 25f, screenWidth, screenHeight),
        Parois(screenWidth - 25f, 0f, screenWidth, screenHeight)
    )

    private var drawing: Boolean = true
    lateinit var canvas: Canvas
    lateinit var thread: Thread

    init {
        backgroundPaint.color = Color.argb(255,93,173,226)
        snake.updatePos(airplane)
        for (i in 1..20) clouds.add(Cloud(random.nextFloat()*screenWidth*0.95f, random.nextFloat()*screenHeight*0.95f,140f, 70f, context))
    }


    override fun run() {
        while (drawing) {
            airplane.bouge(lesParois)
            snake.update(clouds, airplane)
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
            for (cloud in clouds) {
                if (RectF.intersects(airplane.getRect(), cloud.getRect())) {
                    snake.ellongate(cloud, airplane)
                    clouds.remove(cloud)
                    break
                }
            }
            for (elem in snake.getSnake()) elem.draw(canvas)
            for (cloud in clouds) {
                cloud.draw(canvas)
            }
            for (p in lesParois) p.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }
}