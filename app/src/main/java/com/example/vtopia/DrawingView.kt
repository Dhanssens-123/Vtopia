package com.example.vtopia

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Display
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import kotlin.math.pow

class DrawingView  @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), SurfaceHolder.Callback, Runnable {

    lateinit var thread: Thread
    lateinit var canvas: Canvas

    val backgroundPaint = Paint()
    var drawing: Boolean = true

    val displayMetrics = DisplayMetrics()
    var w = context.resources.displayMetrics.widthPixels.toFloat()
    var h = context.resources.displayMetrics.heightPixels.toFloat()

    val n = 5 // Nombre impair

    var damier = Damier(context, w, n)
    var therm_score = Icone(w/2,100F,400F,150F,BitmapFactory.decodeResource(context.resources, R.drawable.score_therm_in),BitmapFactory.decodeResource(context.resources, R.drawable.score_therm_out))
    var star1 = Icone(0.4F*w,275F,100F,100F,BitmapFactory.decodeResource(context.resources, R.drawable.star_yellow),BitmapFactory.decodeResource(context.resources, R.drawable.star_bord))
    var star2 = Icone(w/2,275F,100F,100F,BitmapFactory.decodeResource(context.resources, R.drawable.star_yellow),BitmapFactory.decodeResource(context.resources, R.drawable.star_bord))
    var star3 = Icone(0.6F*w,275F,100F,100F,BitmapFactory.decodeResource(context.resources, R.drawable.star_bord),null)

    var square_green = Icone(w/6,h-575F,150F,150F,BitmapFactory.decodeResource(context.resources, R.drawable.square_green),BitmapFactory.decodeResource(context.resources, R.drawable.square_bord))
    var square_yellow = Icone(w/6,h-400F,150F,150F,BitmapFactory.decodeResource(context.resources, R.drawable.square_yellow),BitmapFactory.decodeResource(context.resources, R.drawable.square_bord))
    var square_corail = Icone(2*w/6,h-400F,150F,150F,BitmapFactory.decodeResource(context.resources, R.drawable.square_corail),BitmapFactory.decodeResource(context.resources, R.drawable.square_bord))
    var square_brown = Icone(3*w/6,h-400F,150F,150F,BitmapFactory.decodeResource(context.resources, R.drawable.square_brown),BitmapFactory.decodeResource(context.resources, R.drawable.square_bord))
    var square_grey = Icone(4*w/6,h-400F,150F,150F,BitmapFactory.decodeResource(context.resources, R.drawable.square_grey),BitmapFactory.decodeResource(context.resources, R.drawable.square_bord))
    var square_pink = Icone(5*w/6,h-400F,150F,150F,BitmapFactory.decodeResource(context.resources, R.drawable.square_pink),BitmapFactory.decodeResource(context.resources, R.drawable.square_bord))
    var square_blue = Icone(5*w/6,h-575F,150F,150F,BitmapFactory.decodeResource(context.resources, R.drawable.square_blue),BitmapFactory.decodeResource(context.resources, R.drawable.square_bord))

    var param = Icone(0.1F*w,100F,100F,100F,BitmapFactory.decodeResource(context.resources, R.drawable.param),null)
    var play_pause = Icone(0.9F*w,100F,100F,100F,BitmapFactory.decodeResource(context.resources, R.drawable.play),null)

    init {
        backgroundPaint.color = Color.argb(255,93,173,226)
    }

    override fun run() {
        damier.setDamier(n)
        while(drawing) {
            draw()
        }
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0F, 0F, canvas.getWidth()*1F, canvas.getHeight()*1F, backgroundPaint)
            for (ligne in damier.cases) {
                for (case in ligne) case.draw(canvas)
            }
            therm_score.draw(canvas)
            star1.draw(canvas)
            star2.draw(canvas)
            star3.draw(canvas)
            square_blue.draw(canvas)
            square_brown.draw(canvas)
            square_corail.draw(canvas)
            square_green.draw(canvas)
            square_grey.draw(canvas)
            square_pink.draw(canvas)
            square_yellow.draw(canvas)
            param.draw(canvas)
            play_pause.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            val x = event.rawX
            val y = event.rawY - 200
            for (ligne in damier.cases) {
                for (case in ligne) {
                    if (case.r.contains(x,y)) case.changeState()
                }
            }
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        thread = Thread(this)
        thread.start()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }
}