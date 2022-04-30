package com.example.vtopia

class GameManager {

    /*
    Contient toutes les informations relatives à la partie en cours
     */

    // Contient les valeurs de base attribuées à chaque type de case
    var dataValueSet = mutableMapOf<String, Int>(
        "forêt" to 10,
        "désert" to 0,
        "habitat" to 20,
        "culture" to 10,
        "industrie" to 20
    )

    // Initialise la partie
    var totalTime = 60.0
    var oneSec = 1.0
    var level = 0
    var prevScore = 0
    var score = 0
    var deltaScore = 0
    var gameOver = false // Partie en cours

    fun updateScore(damier: Damier, icon_score : IconScore, delta : Delta) {
        // Calcule le score total et le met à jour sur l'icone du score
        prevScore = icon_score.score
        score = 0
        icon_score.score = 0
        // Modification des valeurs pour chaque type selon les différentes combinaisons de types de case à l'écran
        dataValueSet["forêt"] = 10*(damier.dataSet["habitat"]!! + 1)
        dataValueSet["habitat"] = 20*(damier.dataSet["culture"]!! + 1)
        dataValueSet["industrie"] = 20 - 10*(damier.dataSet["industrie"]!! - damier.dataSet["habitat"]!!)

        for ((key, value) in damier.dataSet) {
            score += value * dataValueSet[key]!!
        }
        deltaScore = score - prevScore
        delta.updateBloc(deltaScore)
        icon_score.score = score
    }

    fun updateTotalTime(elapsedTime : Double, icon_time : IconTime, money : Money) {
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
        if (oneSec < 0) {
            money.updateBloc(1)
            oneSec = 1.0
        }
    }

    fun reset() {
        // Réinitialise la partie
        score = 0
        level = 0
        totalTime = 60.0
        gameOver = false
    }
}
//Nikzebi