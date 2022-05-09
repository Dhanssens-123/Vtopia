package com.example.vtopia
import kotlin.random.*

class GameManager(_damier: Damier) {
    /*
    Contient toutes les informations relatives à la partie en cours
     */
    private val damier = _damier
    // Contient les valeurs de base attribuées à chaque type de case
    private var dataValueSet = mutableMapOf<String, Int>(
        "forêt" to 2,
        "désert" to 0,
        "habitat" to 2,
        "culture" to 1,
        "feu" to 0,
        "industrie" to 4
    )

    // Initialise la partie
    private var totalTime = 60.0
    private var oneSec = 1.0
    private var ScoreTime = 1.0
    private var level = 1
    private var TotalScore = 0
    private var deltaScore = 0
    private var gameOver = false // Partie en cours ?
    private val random = Random

    fun updateDeltaScore(icon_score : IconScore, delta : IconDelta) {

        // Modification des valeurs pour chaque type selon les différentes combinaisons de types de case à l'écran
        deltaScore = 0

        if (damier.getDataSet()["industrie"]!! > damier.getDataSet()["forêt"]!!)
            dataValueSet["industrie"] = -4
        else
            dataValueSet["industrie"] = 4

        if (damier.getDataSet()["habitat"]!! >= damier.getDataSet()["industrie"]!!)
            dataValueSet["habitat"] = -2
        else dataValueSet["habitat"] = 2

        if (damier.getDataSet()["culture"]!! > damier.getDataSet()["habitat"]!!)
            dataValueSet["culture"] = 1
        else dataValueSet["culture"] = 4

        for ((key, value) in damier.getDataSet()) {
            deltaScore += value * dataValueSet[key]!!
        }

        // Met à jour l'affichage de l'incrément de score
        delta.updateBloc(deltaScore)
    }

    fun updateTotalScore(icon_score: IconScore) {
        // Màj du score
        TotalScore += deltaScore
        icon_score.changeScore(TotalScore)

        // Gère la suppression des cases muables
        var case = damier.getCases()[random.nextInt(damier.getCases().size)][random.nextInt(damier.getCases().size)]
        if (TotalScore < 0 && random.nextInt(2) == 1 && case.isVisible()) {
            case.bruleCase()
        } else if (deltaScore < 0 && random.nextInt(4) == 1 && case.isVisible()) {
            case.bruleCase()
        }
    }


    fun updateTotalTime(elapsedTime : Double, icon_time : IconTime, money : IconMoney, icon_score: IconScore) {

        // Calcule constamment le temps restant et le met à jour sur le timer tant que celui-ci est positif
        totalTime -= elapsedTime / 1000.0
        if (totalTime <= 0) {
            // Le jeu est terminé
            gameOver = true
        } else icon_time.changeTime(totalTime)

        // Met à jour chaque seconde les ressources et le score
        oneSec -= elapsedTime / 1000.0
        ScoreTime -= elapsedTime / 1000.0
        if (oneSec < 0) {
            money.updateBloc(1)
            oneSec = 1.0
        }
        if (ScoreTime < 0) {
            updateTotalScore(icon_score)
            ScoreTime = 1.0
        }
    }

    fun reset() {
        // Réinitialise la partie
        TotalScore = 0
        level = 1
        totalTime = 60.0
        oneSec = 1.0
        ScoreTime = 1.0
        gameOver = false
        deltaScore = 0
    }

    fun getGameOverState() : Boolean {
        return gameOver
    }

    fun getTotalScore() : Int {
        return TotalScore
    }

    fun setLevel(lvl: Int) {
        level = lvl
    }

    fun getLevel() : Int {
        return level
    }
}