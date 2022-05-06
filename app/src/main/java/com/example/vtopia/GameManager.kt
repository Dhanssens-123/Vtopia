package com.example.vtopia
import kotlin.random.*

class GameManager {

    /*
    Contient toutes les informations relatives à la partie en cours
     */

    // Contient les valeurs de base attribuées à chaque type de case
    private var dataValueSet = mutableMapOf<String, Int>(
        "forêt" to 1,
        "désert" to 0,
        "habitat" to 2,
        "culture" to 1,
        "feu" to 0,
        "industrie" to 5
    )

    // Initialise la partie
    private var totalTime = 60.0
    private var oneSec = 1.0
    private var ScoreTime = 1.0
    private var level = 0
    private var TotalScore = 0
    private var deltaScore = 0
    private var gameOver = false // Partie en cours
    private val random = Random

    fun updateDeltaScore(damier: Damier, icon_score : IconScore, delta : Delta) {
        // Calcule le DeltaScore

        // Modification des valeurs pour chaque type selon les différentes combinaisons de types de case à l'écran
        deltaScore = 0
        if (damier.getDataSet()["industrie"]!! > damier.getDataSet()["forêt"]!!)
            dataValueSet["industrie"] = -5
        else
            dataValueSet["industrie"] = 5
            //bruleCase(damier, damier.cases[2][3])

        if (damier.getDataSet()["habitat"]!! > damier.getDataSet()["industrie"]!! +1 )
            dataValueSet["habitat"] = -2
        else dataValueSet["habitat"] = 2

        if (damier.getDataSet()["culture"]!! > 6 )
            dataValueSet["culture"] = 1
        else dataValueSet["culture"] = 5

        for ((key, value) in damier.getDataSet()) {
            deltaScore += value * dataValueSet[key]!!
        }

        println(deltaScore)
        delta.updateBloc(deltaScore)

    }

    fun updateTotalScore(icon_score: IconScore, damier: Damier) {
        TotalScore += deltaScore
        icon_score.changeScore(TotalScore)
        damier.getCases()[random.nextInt(3)][random.nextInt(3)].bruleCase(true)
    }


    fun updateTotalTime(elapsedTime : Double, icon_time : IconTime, money : Money, icon_score: IconScore, damier: Damier) {
        // Calcule le temps restant et le met à jour sur l'icone timer tant que celui-ci est positif
        totalTime -= elapsedTime / 1000.0
        if (totalTime < 0) {
            // Le jeu est terminé
            gameOver = true
        }
        else {
            icon_time.changeTime(totalTime)
        }
        oneSec -= elapsedTime / 1000.0
        ScoreTime -= elapsedTime / 1000.0
        if (oneSec < 0) {
            money.updateBloc(1)
            oneSec = 1.0
        }
        if (ScoreTime < 0) {
            updateTotalScore(icon_score, damier)
            ScoreTime = 1.0
        }
    }

    fun reset() {
        // Réinitialise la partie
        TotalScore = 0
        level = 0
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
}