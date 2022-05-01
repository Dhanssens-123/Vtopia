package com.example.vtopia
import kotlin.random.*

class GameManager {

    /*
    Contient toutes les informations relatives à la partie en cours
     */

    // Contient les valeurs de base attribuées à chaque type de case
    var dataValueSet = mutableMapOf<String, Int>(
        "forêt" to 1,
        "désert" to 0,
        "habitat" to 2,
        "culture" to 1,
        "feu" to 0,
        "industrie" to 5
    )

    // Initialise la partie
    var totalTime = 60.0
    var oneSec = 1.0
    var ScoreTime = 0.5
    var level = 0
    var TotalScore = 0
    var deltaScore = 0
    var gameOver = false // Partie en cours
    val random = Random

    fun updateDeltaScore(damier: Damier, icon_score : IconScore, delta : Delta) {
        // Calcule le DeltaScore

        // Modification des valeurs pour chaque type selon les différentes combinaisons de types de case à l'écran
        deltaScore = 0
        if (damier.dataSet["industrie"]!! > damier.dataSet["forêt"]!!)
            dataValueSet["industrie"] = -5
        else
            dataValueSet["industrie"] = 5
            //bruleCase(damier, damier.cases[2][3])

        if (damier.dataSet["habitat"]!! > damier.dataSet["industrie"]!! +1 )
            dataValueSet["habitat"] = -2
        else dataValueSet["habitat"] = 2

        if (damier.dataSet["culture"]!! > 6 )
            dataValueSet["culture"] = 1
        else dataValueSet["culture"] = 5

        for ((key, value) in damier.dataSet) {
            deltaScore += value * dataValueSet[key]!!
        }

        println(deltaScore)
        delta.updateBloc(deltaScore)

    }

    fun updateTotalScore(icon_score: IconScore) {
        TotalScore += deltaScore
        icon_score.score = TotalScore
    }


    fun updateTotalTimeAndScore(elapsedTime : Double, icon_time : IconTime, money : Money, icon_score: IconScore ) {
        // Calcule le temps restant et le met à jour sur l'icone timer tant que celui-ci est positif
        totalTime -= elapsedTime / 1000.0
        if (totalTime < 0) {
            // Le jeu est terminé
            gameOver = true
        }
        else {
            icon_time.time = totalTime
        }
        oneSec -= elapsedTime / 1000.0
        ScoreTime -= elapsedTime / 1000.0
        if (oneSec < 0) {
            money.updateBloc(1)
            oneSec = 1.0
        }
        if (ScoreTime < 0) {
            updateTotalScore(icon_score)
            ScoreTime = 0.5

        }
    }

    fun reset() {
        // Réinitialise la partie
        TotalScore = 0
        level = 0
        totalTime = 60.0
        gameOver = false
        deltaScore = 0
    }
}