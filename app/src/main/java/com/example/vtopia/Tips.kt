package com.example.vtopia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Tips : AppCompatActivity() {

    lateinit var tipsView : TipsView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)
        tipsView = findViewById<TipsView>(R.id.vTips)
    }

    override fun onPause() {
        super.onPause()
        tipsView.pause()
    }

    override fun onResume() {
        super.onResume()
        tipsView.resume()
    }
}