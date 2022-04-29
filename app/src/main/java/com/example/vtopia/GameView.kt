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

class GameView  @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), SurfaceHolder.Callback, Runnable {

    lateinit var thread: Thread
    lateinit var canvas: Canvas
    /*
    La classe Canvas contient les appels "draw". Pour dessiner quelque chose, vous avez besoin de 4 composants de base :
    Un bitmap pour contenir les pixels, un canevas pour accueillir les appels de dessin (écriture dans le bitmap),
    une primitive de dessin (par exemple, Rect, Path, texte, bitmap) et une peinture (pour décrire les couleurs et les styles du dessin).
    */
    val activity = context as FragmentActivity

    val backgroundPaint = Paint() // Couleur de fond d'écran
    var drawing: Boolean = true // Gère ou non la màj de l'affichage

    // Prend les dimensions de la drawingView ( != dimensions de l'écran total)
    val displayMetrics = DisplayMetrics()
    var w = context.resources.displayMetrics.widthPixels.toFloat()
    var h = context.resources.displayMetrics.heightPixels.toFloat()

    var n = 9 // Diamètre du damier (impair)

    var type = "désert" // Type de la case à placer

    // Création du damier et des icones
    var game = GameManager()
    var damier = Damier(context, w, h, n)
    var squares = arrayOf(
        BtnCase(w/6,h-375F,150F,150F,context,"forêt",damier),
        BtnCase(w/6,h-200F,150F,150F,context,"désert",damier),
        BtnCase(2*w/6,h-200F,150F,150F,context,"extraction",damier),
        BtnCase(3*w/6,h-200F,150F,150F,context,"habitat",damier),
        BtnCase(4*w/6,h-200F,150F,150F, context,"industrie",damier),
        BtnCase(5*w/6,h-200F,150F,150F, context, "culture",damier),
        BtnCase(5*w/6,h-375F,150F,150F, context, "lac",damier)
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
        var previousFrameTime = System.currentTimeMillis() // t_0
        while(drawing) {
            // Temps écoulé
            val currentTime = System.currentTimeMillis()
            var elapsedTimeMS = (currentTime - previousFrameTime).toDouble()
            // Màj du jeu et de l'affichage
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
            canvas.drawRect(0F, 0F, canvas.getWidth()*1F, canvas.getHeight()*1F, backgroundPaint) // Fond d'écran
            for (ligne in damier.cases) {
                for (case in ligne) case.draw(canvas)
            }
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
            // Cesse la mise à jour du jeu et de l'affichage
            drawing = false
            // Affiche les scores et propose de relancer la partie
            showGameOverDialog(R.string.gameOver)
        }
    }

    fun newGame() {
        // Relance une nouvelle partie
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
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                // Récupère les coordonnées du click sur l'écran et vérifie la correspondance
                val x = event.rawX
                val y = event.rawY - 75
                checkClick(squares, damier.cases, x, y)
            }
        }
        return true
    }

    fun checkClick(squares : Array<BtnCase>, cases : Array<Array<Case>>, x : Float, y : Float) {
        var flag = true
        // Si une case est clickée, changement de son état
        for (ligne in cases) {
            for (case in ligne) {
                if (case.r.contains(x,y) && flag) {
                    case.changeType(type)
                    flag = false
                }
            }
        }
        for (square in squares) {
            if (square.r.contains(x,y) && flag) {
                // Si un des carré est clické, réinitialise l'ensemble et affiche le nouveau
                for (s in squares) s.reinit()
                type = square.type // changeIconeType()
                square.changeRect(25)
                flag = false
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
        /*
        Fonction directement copiée du jeu CanonView. A comprendre en détail.
        Permet l'affichage du fragment en fin de jeu.
         */
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