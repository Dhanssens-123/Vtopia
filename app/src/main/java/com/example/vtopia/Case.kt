package com.example.vtopia

import android.content.Context
import android.content.res.Resources
import android.graphics.*

class Case (x: Float, y: Float, diametre: Float, _type: String, _bord: Int, context: Context) {

    private var type = _type
    private var bord = _bord
    private var visible = false // Etat d'affichage de la case
    private var freeze = false // Case muable ?

    // Attribue une image et un bord à chaque type de case
    private val spriteSet = mapOf<String, Bitmap>(
        "forêt" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_green),
        "désert" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_yellow),
        "habitat" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_brown),
        "culture" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_pink),
        "industrie" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_grey),
        "feu" to BitmapFactory.decodeResource(context.resources, R.drawable.hex_corail)
    )
    private val spriteBord = mapOf<Int, Bitmap>(
        0 to BitmapFactory.decodeResource(context.resources, R.drawable.hex_bord),
        1 to BitmapFactory.decodeResource(context.resources, R.drawable.hex_bord_green),
        2 to BitmapFactory.decodeResource(context.resources, R.drawable.hex_bord_red)
    )

    // Définit les différents paramètres pour l'affichage du canvas via draw()
    private var r = RectF(x - diametre/2, y - diametre/Math.sqrt(3.0).toFloat(), x + diametre/2, y + diametre/Math.sqrt(3.0).toFloat())
    private val paint = Paint()

    fun draw(canvas: Canvas?) {
        // Dessine la case
        if (visible) {
            canvas?.drawBitmap(spriteSet[type]!!, null, r, paint)
            canvas?.drawBitmap(spriteBord[bord]!!, null, r, paint)
        }
    }

    fun bruleCase() {
        changeType("feu")
        freeze = true
        bord = 2
    }

    fun changeType(newtype : String) {
        type = newtype
    }

    fun getType() : String {
        return type
    }

    fun setType(newType : String) {
        type = newType
    }

    fun setVisible(_visible : Boolean){
        visible = _visible
    }

    fun isVisible() : Boolean {
        return visible
    }

    fun isFreeze() : Boolean {
        return freeze
    }

    fun setFreeze(bool: Boolean) {
        freeze = bool
    }

    fun setBord(int : Int) {
        bord = int
    }

    fun getRect() : RectF {
        return r
    }
}