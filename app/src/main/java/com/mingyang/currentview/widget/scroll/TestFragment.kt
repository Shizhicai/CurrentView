package com.mingyang.currentview.widget.scroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mingyang.currentview.R

class TestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test, container, false)
        val rvTest = view.findViewById<RecyclerView>(R.id.rv_test)
        rvTest.layoutManager = LinearLayoutManager(context)
        val list = arrayListOf<String>()
        for (i in 0..30) {
            list.add("test item $i")
        }
        rvTest.adapter = TestAdapter(
            list
        )
        return view
    }
}