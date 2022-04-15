package com.example.vtopia

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Display
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import kotlin.math.pow

class DrawingView  @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), SurfaceHolder.Callback, Runnable {

    lateinit var thread: Thread
    lateinit var canvas: Canvas
    val activity = context as FragmentActivity

    val backgroundPaint = Paint()
    var drawing: Boolean = true

    // Prend les dimensions de la drawingView
    val displayMetrics = DisplayMetrics()
    var w = context.resources.displayMetrics.widthPixels.toFloat()
    var h = context.resources.displayMetrics.heightPixels.toFloat()

    val n = 9 // Entier impair

    var type = "désert"

    // Création du damier et des icones
    var game = GameManager()
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
        IconLevel(0.33F*w,h-375F,125F,125F,context,1),
        IconLevel(w/2,h-375F,125F,125F,context,1),
        IconLevel(0.66F*w,h-375F,125F,125F,context,0)
    )

    var therm_score = IconScore(w/2,150F,400F,150F,context,"therm_fill")
    var time_score = IconTime(w/2, 325F,80F,100F,context)

    init {
        backgroundPaint.color = Color.argb(255,93,173,226)
    }

    override fun run() {
        // Création du damier et affichage
        damier.setDamier(n)
        var previousFrameTime = System.currentTimeMillis()
        while(drawing) {
            val currentTime = System.currentTimeMillis()
            var elapsedTimeMS = (currentTime - previousFrameTime).toDouble()
            game.updateTotalTime(elapsedTimeMS, time_score)
            damier.changeDataSet()
            game.updateScore(damier, therm_score)
            draw()
            previousFrameTime = currentTime
            checkGameOver()
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
            time_score.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun checkGameOver() {
        if (game.gameOver) {
            drawing = false
            showGameOverDialog(R.string.gameOver)
        }
    }

    fun newGame() {
        type = "désert"
        game.reset()
        damier.reset()
        drawing = true
        thread = Thread(this)
        thread.start()
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
                checkClick(squares, damier.cases, x, y)
            }
        }
        return true
    }

    fun checkClick(squares : Array<BtnCase>, cases : Array<Array<Case>>, x : Float, y : Float) {
        var flag = true
        for (square in squares) {
            if (square.r.contains(x,y) && flag) {
                type = square.type
                flag = false
            }
        }
        for (ligne in cases) {
            for (case in ligne) {
                if (case.r.contains(x,y) && flag) {
                    case.type = type
                    flag = false
                }
            }
        }
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

    fun showGameOverDialog(messageId: Int) {
        class GameResult: DialogFragment() {
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle(resources.getString((messageId)))
                builder.setMessage(resources.getString(R.string.results_format, game.score))
                builder.setPositiveButton(R.string.reset_game, DialogInterface.OnClickListener { _, _->newGame()})
                return builder.create()
            }
        }

        activity.runOnUiThread(
            Runnable {
                val ft = activity.supportFragmentManager.beginTransaction()
                val prev = activity.supportFragmentManager.findFragmentByTag("dialog")
                if (prev != null) {
                    ft.remove(prev)
                }
                ft.addToBackStack(null)
                val gameResult = GameResult()
                gameResult.setCancelable(false)
                gameResult.show(ft,"dialog")
            }
        )
    }
}