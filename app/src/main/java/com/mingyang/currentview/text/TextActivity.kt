package com.mingyang.currentview.text

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.viewpager.widget.PagerAdapter
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
            tv.setPercentage(0f, true)
            llTab.addView(tv)
        }

        (llTab.getChildAt(currentPosition) as CurrentTextView).apply {
            setPercentage(0f, false)
        }
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            var lastValue: Float = 0f
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // 需要处理中间位置
                if (positionOffset > 0 && lastValue != 0f) {
                    if (lastValue >= positionOffset) {
                        // 向左
                        (llTab.getChildAt(currentPosition) as CurrentTextView).setPercentage(
                            positionOffset,
                            true
                        )
                        if (currentPosition != 0)
                            (llTab.getChildAt(currentPosition - 1) as CurrentTextView).setPercentage(
                                positionOffset,
                                false
                            )
                    } else {

                        // 向右
                        (llTab.getChildAt(currentPosition) as CurrentTextView).setPercentage(
                            positionOffset,
                            false
                        )
                        if (currentPosition < vp.size - 1) {
                            (llTab.getChildAt(currentPosition + 1) as CurrentTextView).setPercentage(
                                positionOffset,
                                true
                            )
                        }
                    }
                }
                lastValue = positionOffset
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
                if (state == 0) {
                    // 停止滑动
                    currentPosition = vp.currentItem
                    Log.e(
                        "测试",
                        "onPageScrollStateChanged $state $currentPosition"
                    )
                }
                Log.e(
                    "测试",
                    "onPageScrollStateChanged $state"
                )
            }

        })

    }
}