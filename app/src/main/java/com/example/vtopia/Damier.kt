package com.example.vtopia

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas

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

    private val spriteSet = mapOf<String, Bitmap>(
        "forêt" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_green),
        "désert" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_yellow),
        "habitat" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_brown),
        "culture" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_pink),
        "industrie" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_grey),
        "feu" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_corail),
        "bord" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_bord)
    )

    // Fixe le diamètre du damier et les positions de chaque case pour la création du tableau de cases
    private val diamx = 0.85F * weigth/n
    private val diamy = 3*diamx/(2*Math.sqrt(3.0).toFloat())
    private val posx = Array<Array<Float>>(n, {i -> Array(n, {j -> (weigth/2 - n/2*diamx) + j*diamx + diamx/2*(i%2)})})
    private val posy = Array(n, {i -> (height/2 - n/2*diamy + i*diamy)})
    private val cases = Array<Array<Case>>(n, {i -> Array(n, {j -> Case(posx[i][j],posy[i],diamx,"désert",0,context)})})

    init {
        setDamier(n)
    }

    fun updateDataSet() {
        // Màj du nombre de cases attribuées à chacun des types
        resetDataSet()
        for (ligne in cases) {
            for (case in ligne) {
                dataSet[case.getType()] = dataSet[case.getType()]!! + 1
            }
        }
    }

    fun resetDataSet() {
        for ((key, value) in dataSet) dataSet[key] = 0
    }

    fun setDamier(n: Int) {

        // Dévoile un damier hexagonal circulaire

        var mid = n/2
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
        // Réinitialise le damier
        resetDataSet()
        for (ligne in cases) {
            for (case in ligne) {
                case.setType("désert")
                case.setBord(0)
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