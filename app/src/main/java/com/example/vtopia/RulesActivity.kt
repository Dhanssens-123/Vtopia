package com.example.vtopia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RulesActivity : AppCompatActivity() {

    lateinit private var tipsView : RulesView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)
        tipsView = findViewById<RulesView>(R.id.vTips)
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