package com.example.vtopia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ZoomControls
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var drawingView : DrawingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawingView = findViewById<DrawingView>(R.id.vMain)
    }

    fun gameOnOff(v: View) {
        // Met l'activité drawingView en pause
        if (drawingView.drawing) {
            drawingView.pause()
            play_pause.setImageResource(R.drawable.pause)
        }
        else {
            drawingView.resume()
            play_pause.setImageResource(R.drawable.play)
        }
    }

    fun soundOnOff(v: View) {
        if (drawingView.drawing) {
            drawingView.pause()
            param.setImageResource(R.drawable.outline)
        }
        else {
            drawingView.resume()
            param.setImageResource(R.drawable.param)
        }
    }

    override fun onPause() {
        super.onPause()
        drawingView.pause()
    }

    override fun onResume() {
        super.onResume()
        drawingView.resume()
    }
}