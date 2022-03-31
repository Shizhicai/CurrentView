package com.mingyang.currentview.widget.decotation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mingyang.currentview.R

class TestDecorationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_decoration)

        initData()
    }

    private fun initData() {
        val rvDecoration = findViewById<RecyclerView>(R.id.rv_decoration)

        val data = arrayListOf<TestData>()
        for (i in 0..4) {
            for (j in 0..10) {
                data.add(TestData("测试$i == $j", "标题 $i ====="))
            }
        }
        rvDecoration.layoutManager = LinearLayoutManager(this)
        rvDecoration.adapter = TestAdapter(data)
        rvDecoration.addItemDecoration(TestDecoration(this))
    }


}