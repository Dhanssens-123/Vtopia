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

class GameView  @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), Runnable {

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
    var screenWidth = context.resources.displayMetrics.widthPixels.toFloat()
    var screenHeight = context.resources.displayMetrics.heightPixels.toFloat()

    val n = 5 // Diamètre du damier (impair)

    var type = "désert" // Type de la case à placer

    // Création du damier et des icones
    var game = GameManager()
    var damier = Damier(context, screenWidth, screenHeight, n)
    var squares = arrayOf(
        BtnCase(screenWidth/6,screenHeight-200F,150F,150F,context,"désert",damier),
        BtnCase(2*screenWidth/6,screenHeight-200F,150F,150F,context,"culture",damier),
        BtnCase(3*screenWidth/6,screenHeight-200F,150F,150F,context,"habitat",damier),
        BtnCase(4*screenWidth/6,screenHeight-200F,150F,150F, context,"industrie",damier),
        BtnCase(5*screenWidth/6,screenHeight-200F,150F,150F, context, "forêt",damier)
    )
    var money = Money(screenWidth/2, screenHeight-525F, 150F + 4*screenWidth/6, 100F, context)
    var delta = Delta(screenWidth/2, screenHeight-375F,150F + 4*screenWidth/6, 100F, context)
    var iconCity = IconCity(screenWidth/2,150F,500F,150F, context)
    var therm_score = IconScore(0.875F*screenWidth, 350F, 90F, 100F, context)
    var time_score = IconTime(0.125F*screenWidth, 350F,90F,100F,context)

    init {
        backgroundPaint.color = Color.argb(255,93,173,226)
    }

    override fun run() {
        damier.setDamier(n)
        var previousFrameTime = System.currentTimeMillis() // t_0
        while(drawing) {
            // Temps écoulé
            val currentTime = System.currentTimeMillis()
            var elapsedTimeMS = (currentTime - previousFrameTime).toDouble()
            // Màj du jeu et de l'affichage
            game.updateTotalTimeAndScore(elapsedTimeMS, time_score, money, therm_score)
            damier.changeDataSet()
            game.updateDeltaScore(damier, therm_score, delta)
            //game.bruleCase(damier, damier.cases)
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
            money.draw(canvas)
            delta.draw(canvas)
            iconCity.draw(canvas)
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
                if (case.r.contains(x,y) && flag && money.nbreBloc >= 2) {
                    case.changeType(type)
                    money.updateBloc(-2)
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

    fun showGameOverDialog(messageId: Int) {
        /*
        Fonction directement copiée du jeu CanonView. A comprendre en détail.
        Permet l'affichage du fragment en fin de jeu.
         */
        class GameResult: DialogFragment() {
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle(resources.getString((messageId)))
                builder.setMessage(resources.getString(R.string.results_format, game.TotalScore))
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