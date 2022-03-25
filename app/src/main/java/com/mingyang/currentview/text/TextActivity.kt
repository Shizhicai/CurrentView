package com.mingyang.currentview.text

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mingyang.currentview.R
import com.mingyang.currentview.scroll.TestFragment

class TextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

//        val tabLayout: TabLayout = findViewById(R.id.tablayout)
        val vp: ViewPager = findViewById(R.id.vp)
        vp.adapter = TestViewPager(
            supportFragmentManager,
            arrayListOf(TestFragment(), TestFragment(), TestFragment())
        )
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.e(
                    "测试",
                    "positionOffset $positionOffset  positionOffsetPixels$positionOffsetPixels"
                )
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }
}