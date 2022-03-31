package com.mingyang.currentview.widget.decotation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mingyang.currentview.R

class TestAdapter(
    private val list: ArrayList<TestData>
) : RecyclerView.Adapter<TestAdapter.TestItemViewHolder>() {

    class TestItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv)
    }

    fun isHeadGroup(position: Int): Boolean =
        if (position == 0)
            true
        else
            list[position].groupName != list[position - 1].groupName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestItemViewHolder =
        TestItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false)
        )

    override fun onBindViewHolder(holder: TestItemViewHolder, position: Int) {
        holder.tvName.text = list[position].name
    }

    override fun getItemCount(): Int = list.size

    fun findPositionByGroupName(position: Int) = list[position].groupName
}