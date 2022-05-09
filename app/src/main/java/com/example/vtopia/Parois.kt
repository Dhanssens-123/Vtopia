package com.example.vtopia

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Parois (x1: Float, y1: Float, x2: Float, y2: Float) {
    /*
    Borne l'écran et empêche l'objet AirPlane de passer à travers
     */
    private val r = RectF(x1, y1, x2, y2)

    fun gereAirPlane(airplane: AirPlane) {
        if (RectF.intersects(r, airplane.getRect())) {
            if (r.width() > r.height()) {
                airplane.changeDirection (true)
            }
            else {
                airplane.changeDirection(false)
            }
        }
    }

}