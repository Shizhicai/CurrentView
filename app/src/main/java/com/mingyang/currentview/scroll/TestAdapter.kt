package com.mingyang.currentview.scroll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mingyang.currentview.R

class TestAdapter(private val list: ArrayList<String>) :
    RecyclerView.Adapter<TestAdapter.TestViewHolder>() {


    class TestViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder =
        TestViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_test,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        (holder.itemView as TextView).text = list[position]
    }

    override fun getItemCount(): Int = list.size

}