package com.example.vtopia

import android.content.Context
import java.util.*

class Damier (context: Context, weigth: Float, height: Float, n: Int)  {

    // Attribue une valeur et une image à chaque type de case
    private var dataSet = mutableMapOf<String, Int>(
        "forêt" to 0,
        "désert" to 0,
        "habitat" to 0,
        "culture" to 0,
        "industrie" to 0,
        "feu" to 0
    )

    // Fixe le diamètre et les positions de chaque case pour la création du damier
    private val diamx = 0.85F * weigth/n
    private val diamy = 3*diamx/(2*Math.sqrt(3.0).toFloat())
    private val posx = Array<Array<Float>>(n, {i -> Array(n, {j -> (weigth/2 - n/2*diamx) + j*diamx + diamx/2*(i%2)})})
    private val posy = Array(n, {i -> (height/2 - n/2*diamy + i*diamy)})
    // Composition : Les cases n'existent qu'au sein du damier. Elles sont reliées à la vie à la mort.
    private val cases = Array<Array<Case>>(n, {i -> Array(n, {j -> Case(posx[i][j],posy[i],diamx,"désert",1,context)})})

    private val random = Random()

    init {
        setDamier(n)
    }

    fun updateDataSet() {
        // Màj du nombre de cases attribuées à chacun des types
        resetDataSet()
        for (ligne in cases) {
            for (case in ligne) {
                if (case.isVisible()) dataSet[case.getType()] = dataSet[case.getType()]!! + 1
            }
        }
    }

    fun resetDataSet() {
        for ((key, value) in dataSet) dataSet[key] = 0
    }

    fun setDamier(n: Int) {
        // Dévoile un damier hexagonal circulaire

        val mid = n/2
        // Partie supérieure du damier
        for (i in -mid..0) {
            for (j in (-mid + Math.round((-i-1 + mid%2) / 2.0)).toInt()..(mid - Math.round((-i + mid%2)/ 2.0)).toInt()) {
                cases[mid + i][mid + j].setVisible(true) // Affiche la case correspondante
            }
        }
        // Partie inférieure
        for (i in 1..mid) {
            for (j in (-mid + Math.round((i-1 + mid%2) / 2.0)).toInt()..(mid - Math.round((i + mid%2) / 2.0)).toInt()) {
                cases[mid + i][mid + j].setVisible(true) // Affiche la case correspondante
            }
        }
    }

    fun reset() {
        resetDataSet()
        for (ligne in cases) {
            for (case in ligne) {
                case.setType("désert")
                case.setBord(1)
                case.setFreeze(false)
            }
        }
    }

    fun createRandomCity(game: GameManager) {
        // Impose un nombre déterminé de cases aléatoires selon le niveau choisi

        val intType = mapOf<Int, String>(
            0 to "forêt",
            1 to "habitat",
            2 to "industrie"
        )
        var num = 2*game.getLevel()

        while (num > 0) {
            val case = cases[random.nextInt(cases.size)][random.nextInt(cases.size)]
            if (case.isVisible() && case.getType() == "désert") {
                case.changeType(intType[random.nextInt(3)]!!)
                case.setFreeze(true)
                case.setBord(0)
                num -= 1
            }
        }
    }

    fun getDataSet() : MutableMap<String, Int> {
        return dataSet
    }

    fun getCases() : Array<Array<Case>> {
        return cases
    }
}