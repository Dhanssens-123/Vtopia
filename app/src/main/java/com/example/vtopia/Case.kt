package com.example.vtopia

import android.content.Context
import android.content.res.Resources
import android.graphics.*

class Case (x: Float, y: Float, diametre: Float, _type: String, _bord: Int, context: Context) {

    private var type = _type
    private var bord = _bord
    private var state = false // Etat d'affichage de la case

    // Attribue une image et un bord à chaque type de case
    val spriteSet = mapOf<String, Bitmap>(
        "forêt" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_green),
        "désert" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_yellow),
        "habitat" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_brown),
        "culture" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_pink),
        "industrie" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_grey),
        "feu" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_corail)
    )

    val spriteBord = mapOf<String, Bitmap>(
        "bord" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_bord),
        "bord_green" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_bord_green),
        "bord_red" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_bord_red)
    )

    // Définit les différents paramètres pour l'affichage via draw() du canvas.
    var r = RectF(x - diametre/2, y - diametre/Math.sqrt(3.0).toFloat(), x + diametre/2, y + diametre/Math.sqrt(3.0).toFloat())
    val paint = Paint()
    var paint2 = Paint()
    var color = Color.argb(0,255,255,255)

    fun draw(canvas: Canvas?) {
        // Dessine la case
        paint2.color = color
        if (state) {
            canvas?.drawBitmap(spriteSet[type]!!, null, r, paint)
            if (bord == 0) {
                canvas?.drawBitmap(spriteBord["bord"]!!, null, r, paint)
            } else if (bord == 1) {
                canvas?.drawBitmap(spriteBord["bord_green"]!!, null, r, paint)
            } else if (bord == 2) {
                canvas?.drawBitmap(spriteBord["bord_red"]!!, null, r, paint)
            }
        }
    }

    fun bruleCase(bool : Boolean) {
        if (true)
            state = false
            changeType("feu")
    }

    fun changeType(newtype : String) {
        // Change le type de la case si celle-ci est affichée
        if (state)
            type = newtype
    }

    fun getType() : String {
        return type
    }

    fun setType(newType : String) {
        type = newType
    }

    fun setVisible(visible : Boolean){
        state = visible
    }

    fun setBord(int : Int) {
        bord = int
    }
}