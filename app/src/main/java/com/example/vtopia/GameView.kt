package com.example.vtopia

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import java.util.*

class GameView  @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), Runnable {

    lateinit private var thread: Thread
    lateinit private var canvas: Canvas
    private val random = Random()

    /*
    La classe Canvas contient les appels "draw". Pour dessiner quelque chose, vous avez besoin de 4 composants de base :
    Un bitmap pour contenir les pixels, un canevas pour accueillir les appels de dessin (écriture dans le bitmap),
    une primitive de dessin (par exemple, Rect, Path, texte, bitmap) et une peinture (pour décrire les couleurs et les styles du dessin).
    */

    private val activity = context as FragmentActivity

    private val backgroundPaint = Paint() // Couleur de fond d'écran
    private var drawing: Boolean = true // Gère ou non la màj de l'affichage

    // Récupère les dimensions de la drawingView ( != dimensions de l'écran total)
    private val w = context.resources.displayMetrics.widthPixels.toFloat()
    private val h = context.resources.displayMetrics.heightPixels.toFloat()
    // Récupère la hauteur de la barre de statut
    private val hStatusBar = context.resources.getDimensionPixelSize(context.resources.getIdentifier("status_bar_height","dimen","android"))

    private val DIAM_DAMIER = 5 // Diamètre du damier (impair)
    private val NBRE_CLOUDS = 20

    private var type = "désert" // Type de la case à placer

    // Création du damier et des icones
    private val damier = Damier(context, w, 0.9f*h, DIAM_DAMIER)
    private val game = GameManager(damier)
    private val clouds = ArrayList<Cloud>()
    private val squares = arrayOf(
        ButtonCase(w/6,0.9f*h,w/8,w/8,context,"désert",damier),
        ButtonCase(2*w/6,0.9f*h,w/8,w/8,context,"culture",damier),
        ButtonCase(3*w/6,0.9f*h,w/8,w/8,context,"habitat",damier),
        ButtonCase(4*w/6,0.9f*h,w/8,w/8, context,"industrie",damier),
        ButtonCase(5*w/6,0.9f*h,w/8,w/8, context, "forêt",damier)
    )
    private val money = IconMoney(w/2, 0.725f*h, 4*w/6, h/20, context)
    private val delta = IconDelta(w/2, 0.8f*h,4*w/6, h/20)
    private val iconCity = IconCity(w/2,h/12,w/2,h/10, context)
    private val therm_score = IconScore(7*w/8, h/7, w/10, h/10)
    private val time_score = IconTime(w/8, h/7,w/10,h/10)

    init {
        backgroundPaint.color = Color.argb(255,93,173,226)
        for (i in 1..NBRE_CLOUDS) clouds.add(Cloud(w/20 + random.nextFloat()*9/10*w,
            w/20 + random.nextFloat()*9/10*h,w/6, w/12, context))
    }

    override fun run() {
        var previousFrameTime = System.currentTimeMillis() // t_0
        while(drawing) {
            // Temps écoulé
            val currentTime = System.currentTimeMillis()
            val elapsedTimeMS = (currentTime - previousFrameTime).toDouble()
            // Màj du jeu et de l'affichage
            game.updateTotalTime(elapsedTimeMS, time_score, money, therm_score)
            damier.updateDataSet()
            game.updateDeltaScore(delta)
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
            canvas.drawRect(0F, 0F, w, h, backgroundPaint) // Fond d'écran
            for (cloud in clouds) {
                cloud.draw(canvas)
            }
            for (line in damier.getCases()) {
                for (case in line) case.draw(canvas)
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
        if (game.getGameOverState()) {
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
        damier.createRandomCity(game)
        for (s in squares) {
            s.reinitRectSize(s.getRect())
            s.changeRectSize(s.getRectStroke(),s.getStrokeSize(),s.getStrokeSize())
        }
        time_score.reset()
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
                val y = event.rawY - hStatusBar
                if (drawing) checkClick(x, y)
            }
        }
        return true
    }

    fun checkClick(x : Float, y : Float) {
        var flag = true
        // Si une case est clickée, changement de son état
        for (ligne in damier.getCases()) {
            for (case in ligne) {
                if (flag && case.isVisible() && case.getRect().contains(x,y) && case.getType() != type && !case.isFreeze() && money.getNbreBloc() >= 2) {
                    case.changeType(type)
                    money.updateBloc(-2)
                    flag = false
                }
            }
        }
        for (square in squares) {
            if (square.getRect().contains(x,y) && flag) {
                // Si un des carré est clické, le met en avant et change le type actuel
                grow(square)
                type = square.getType() // changeIconeType()
                flag = false
            }
        }
    }

    fun grow(square: ButtonCase) {
        for (s in squares) {
            s.reinitRectSize(s.getRect())
            s.changeRectSize(s.getRectStroke(),s.getStrokeSize(),s.getStrokeSize())
        }
        square.changeRectSize(square.getRect(), 25f, 25f)
    }

    fun showGameOverDialog(messageId: Int) {
        /*
        Fonction directement récupérée du jeu CanonView.
        Permet l'affichage du fragment en fin de jeu.
         */
        class GameResult: DialogFragment() {
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle(resources.getString((messageId)))
                builder.setMessage(resources.getString(R.string.results_format, game.getTotalScore()))
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

    fun setCityName(name : String) {
        iconCity.setCityName(name)
    }

    fun setLevel(lvl : Int) {
        game.setLevel(lvl)
        damier.createRandomCity(game)
    }

    fun isDrawing() : Boolean {
        return drawing
    }
}