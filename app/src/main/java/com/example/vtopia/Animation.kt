package com.example.vtopia

import android.graphics.Canvas
import android.graphics.Paint
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton

interface Animation {

    fun bounce(obj : ImageButton) {
        var bounce = AnimationUtils.loadAnimation(null, R.anim.bounce)
        obj.startAnimation(bounce)
    }

    fun grow(square: BtnCase, squares: Array<BtnCase>) {
        for (s in squares) s.reinit()
        square.changeRect(25)
    }

    fun blink(txt: String, flag: Int) : String {
        return if (flag % 5 == 0) "" else txt
    }
}
