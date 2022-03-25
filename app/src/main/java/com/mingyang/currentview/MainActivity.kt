package com.mingyang.currentview

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mingyang.currentview.fish.FishDrawable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        initClick()
        val ivFish = findViewById<ImageView>(R.id.iv_fish)
        ivFish.setImageDrawable(FishDrawable())
    }

    private fun initClick() {
//        findViewById<Button>(R.id.btn_scroll).setOnClickListener {
//            startActivity(Intent(this, ScrollViewActivity::class.java))
//        }
    }
}