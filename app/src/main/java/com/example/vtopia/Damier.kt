package com.example.vtopia

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas

class Damier (context: Context, weigth: Float, height: Float, n: Int)  {

    var dataSet = mutableMapOf<String, Int>(
        "forêt" to 0,
        "désert" to 0,
        "lac" to 0,
        "habitat" to 0,
        "culture" to 0,
        "extraction" to 0,
        "industrie" to 0,
    )

    val spriteSet = mapOf<String, Bitmap>(
        "forêt" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_green),
        "désert" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_yellow),
        "lac" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_blue),
        "habitat" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_brown),
        "culture" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_pink),
        "extraction" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_corail),
        "industrie" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_grey),
        "bord" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_bord)
    )

    var diam = 0.85F * weigth/n
    var posx = Array<Array<Float>>(n, {i -> Array(n, {j -> (weigth/2 - n/2*diam) + j*diam + diam/2*(i%2)})})
    var posy = Array(n, {i -> (height/2 - n/2*3*diam/(2*Math.sqrt(3.0).toFloat())) + i*3*diam/(2*Math.sqrt(3.0).toFloat())})
    var cases = Array<Array<Case>>(n, {i -> Array(n, {j -> Case(posx[i][j],posy[i],diam,"désert",0,context)})})

    fun changeDataSet() {
        resetDataSet()
        for (ligne in cases) {
            for (case in ligne) {
                dataSet[case.type] = dataSet[case.type]!! + 1
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
                cases[mid + i][mid + j].state = true
            }
        }
        // Partie inférieure
        for (i in 1..mid) {
            for (j in (-mid + Math.round((i-1 + mid%2) / 2.0)).toInt()..(mid - Math.round((i + mid%2) / 2.0)).toInt()) {
                cases[mid + i][mid + j].state = true
            }
        }
    }
}