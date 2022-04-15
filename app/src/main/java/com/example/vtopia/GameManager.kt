package com.example.vtopia

class GameManager {

    var dataValueSet = mutableMapOf<String, Int>(
        "forêt" to 10,
        "désert" to 0,
        "lac" to 10,
        "habitat" to 20,
        "culture" to 10,
        "extraction" to 5,
        "industrie" to 5
    )

    var totalTime = 60.0
    var level = 0
    var score = 0
    var gameOver = false

    fun updateScore(damier: Damier, icon_score : IconScore) {
        score = 0
        icon_score.score = 0

        dataValueSet["forêt"] = 10*(damier.dataSet["habitat"]!! + 1)
        dataValueSet["lac"] = 10*(damier.dataSet["habitat"]!! + 1)
        dataValueSet["habitat"] = 20*(damier.dataSet["culture"]!! + 1) - 10*(damier.dataSet["habitat"]!! - damier.dataSet["extraction"]!!)

        dataValueSet["industrie"] = 5 - 10*(damier.dataSet["industrie"]!! - damier.dataSet["habitat"]!!)

        for ((key, value) in damier.dataSet) {
            score += value * dataValueSet[key]!!
        }
        icon_score.score = score
    }

    fun updateTotalTime(elapsedTime : Double, icon_time : IconTime) {
        totalTime -= elapsedTime / 1000.0
        if (totalTime < 0) {
            gameOver = true
        }
        else {
            icon_time.time = totalTime
        }
    }

    fun reset() {
        score = 0
        level = 0
        totalTime = 60.0
        gameOver = false
    }
}