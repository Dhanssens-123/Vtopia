package com.example.vtopia

import android.graphics.Canvas
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton

interface Animation {
    abstract var bounce: Animation?

    fun bounce(obj : ImageButton) {
        bounce = AnimationUtils.loadAnimation(null, R.anim.bounce)
        obj.startAnimation(bounce)
    }
}
