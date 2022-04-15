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

    // Prend les dimensions de la drawingView
    val displayMetrics = DisplayMetrics()
    var w = context.resources.displayMetrics.widthPixels.toFloat()
    var h = context.resources.displayMetrics.heightPixels.toFloat()

    val n = 9 // Entier impair

    // Création du damier et des icones
    var damier = Damier(context, w, h, n)
    var squares = arrayOf(
        BtnCase(w/6,h-375F,150F,150F,context,"forêt"),
        BtnCase(w/6,h-200F,150F,150F,context,"désert"),
        BtnCase(2*w/6,h-200F,150F,150F,context,"extraction"),
        BtnCase(3*w/6,h-200F,150F,150F,context,"habitat"),
        BtnCase(4*w/6,h-200F,150F,150F, context,"industrie"),
        BtnCase(5*w/6,h-200F,150F,150F, context, "culture"),
        BtnCase(5*w/6,h-375F,150F,150F, context, "lac")
    )
    var stars = arrayOf(
        IconLevel(0.4F*w,325F,100F,100F,context,1),
        IconLevel(w/2,325F,100F,100F,context,1),
        IconLevel(0.6F*w,325F,100F,100F,context,0)
    )

    var therm_score = IconScore(w/2,150F,400F,150F,context,"therm_fill")

    init {
        backgroundPaint.color = Color.argb(255,93,173,226)
    }

    override fun run() {
        // Création du damier et affichage
        damier.setDamier(n)
        while(drawing) {
            draw()
        }
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            // Affichage des éléments
            canvas.drawRect(0F, 0F, canvas.getWidth()*1F, canvas.getHeight()*1F, backgroundPaint)
            for (ligne in damier.cases) {
                for (case in ligne) case.draw(canvas)
            }
            therm_score.draw(canvas)
            for (elem in squares) {
                elem.draw(canvas)
            }
            for (elem in stars) {
                elem.draw(canvas)
            }
            therm_score.draw(canvas)
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
        // Quand on clique sur l'écran
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.rawX
                val y = event.rawY - 75
                for (ligne in damier.cases) {
                    for (case in ligne) {
                        if (case.r.contains(x,y)) {
                            case.changeState()
                            break
                        }
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.rawX
                val y = event.rawY - 200
                for (ligne in damier.cases) {
                    for (case in ligne) {
                        if (case.r.contains(x,y)) {
                            case.state = false
                            break
                        }
                    }
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