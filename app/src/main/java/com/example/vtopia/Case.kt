package com.example.vtopia

import android.content.Context
import android.content.res.Resources
import android.graphics.*

class Case (_x: Float, _y: Float, _cote: Float, _type: String, _bord: Int, context: Context) {

    var type = _type
    var bord = _bord
    var state = false

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

    var x = _x
    var y = _y
    var diametre = _cote
    var r = RectF(x - diametre/2, y - diametre/Math.sqrt(3.0).toFloat(), x + diametre/2, y + diametre/Math.sqrt(3.0).toFloat())
    val paint = Paint()
    var paint2 = Paint()
    var color = Color.argb(0,255,255,255)

    fun draw(canvas: Canvas?) {
        // Dessine la case
        paint2.color = color
        if (state) {
            if (bord == 0) {
                canvas?.drawBitmap(spriteSet[type]!!, null, r, paint)
                canvas?.drawBitmap(spriteSet["bord"]!!, null, r, paint)
            } else {
                canvas?.drawBitmap(spriteSet["bord"]!!, null, r, paint)
            }
        }
    }

    fun changeState() {
        // Cache ou non la case
        state = !state
    }
}