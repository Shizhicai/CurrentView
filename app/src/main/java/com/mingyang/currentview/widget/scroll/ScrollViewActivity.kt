package com.mingyang.currentview.widget.scroll

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mingyang.currentview.R

class ScrollViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view)
        initView()
    }

    private fun initView() {
        val rvHead = findViewById<RecyclerView>(R.id.rv_head)
        val tablayout = findViewById<TabLayout>(R.id.tablayout)
        val vp = findViewById<ViewPager2>(R.id.vp)

        initHead(rvHead)
        initVp(vp, tablayout)
    }

    private fun initHead(rvHead: RecyclerView) {
        rvHead.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvHead.adapter = TestAdapter(
            arrayListOf(
                "head view 1",
                "head view 2",
                "head view 3",
                "head view 4",
                "head view 5"
            )
        )
    }

    private fun initVp(vp: ViewPager2, tabLayout: TabLayout) {
        vp.adapter =
            TestViewPager(this, arrayListOf(TestFragment(), TestFragment(), TestFragment()))
        val titles = arrayOf("test1", "test2", "test3")
        tabLayout.setTabTextColors(Color.BLACK, Color.RED)
        TabLayoutMediator(
            tabLayout, vp
        ) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}