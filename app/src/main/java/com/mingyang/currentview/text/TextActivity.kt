package com.mingyang.currentview.text

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.mingyang.currentview.R
import com.mingyang.currentview.scroll.TestFragment

class TextActivity : AppCompatActivity() {
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        val llTab: LinearLayout = findViewById(R.id.ll_tab)
        val vp: ViewPager = findViewById(R.id.vp)
        vp.adapter = TestViewPager(
            supportFragmentManager,
            arrayListOf(TestFragment(), TestFragment(), TestFragment())
        )

        for (i in 0..2) {
            val tv = CurrentTextView(this)
            tv.layoutParams = LinearLayout.LayoutParams(0, ViewPager.LayoutParams.MATCH_PARENT, 1f)
            tv.gravity = Gravity.CENTER
            llTab.addView(tv)
        }
        (llTab.getChildAt(currentPosition) as CurrentTextView).setPercentage(1f, true)
        val width = vp.width
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (currentPosition != 0 && currentPosition != position) {
                    if (currentPosition > position) {
                        // 向左
                        (llTab.getChildAt(currentPosition) as CurrentTextView).setPercentage(
                            positionOffset,
                            true
                        )
                        (llTab.getChildAt(currentPosition + 1) as CurrentTextView).setPercentage(
                            1 - positionOffset,
                            false
                        )
                    } else {
                        // 向右
                        (llTab.getChildAt(currentPosition) as CurrentTextView).setPercentage(
                            1f - positionOffset,
                            false
                        )
                        (llTab.getChildAt(currentPosition + 1) as CurrentTextView).setPercentage(
                            positionOffset,
                            true
                        )
                    }
                }


                Log.e(
                    "测试",
                    "positionOffset $positionOffset  positionOffsetPixels$positionOffsetPixels position$position"
                )
            }

            override fun onPageSelected(position: Int) {
//                currentPosition = position
                Log.e(
                    "测试",
                    "onPageSelected $position"
                )
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.e(
                    "测试",
                    "onPageScrollStateChanged $state"
                )
            }

        })

    }
}