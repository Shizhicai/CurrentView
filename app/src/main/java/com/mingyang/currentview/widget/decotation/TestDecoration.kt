package com.mingyang.currentview.widget.decotation

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TestDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private var headHeight: Int
    private var mPaint: Paint
    private var mTextPaint: Paint

    init {
        headHeight = dip2px(context, 50)
        mPaint = Paint()
//        mPaint.textSize = 15f
        mPaint.color = Color.RED
        mTextPaint = Paint()
        mTextPaint.textSize = dip2px(context, 15).toFloat()
        mTextPaint.color = Color.WHITE
    }

    private fun dip2px(context: Context, dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue.toFloat() * scale + 0.5f).toInt()
    }


    // onDraw -> itemView -> onDrawOver
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val adapter = parent.adapter
        if (adapter is TestAdapter) {
            val left = parent.paddingLeft.toFloat()
            val right = parent.width - parent.paddingRight.toFloat()
            for (i in 0..parent.childCount) {
                val view = parent.getChildAt(i)
                val position = parent.getChildLayoutPosition(view)
                if (position != -1) {
                    if (adapter.isHeadGroup(position)) {
                        c.drawRect(
                            left,
                            view.top - headHeight.toFloat(),
                            right,
                            view.top.toFloat(),
                            mPaint
                        )
                        val groupName = adapter.findPositionByGroupName(position)
                        val rect = Rect()
                        mTextPaint.getTextBounds(groupName, 0, groupName.length, rect)
                        c.drawText(
                            groupName,
                            left + 20,
                            (view.top - headHeight / 2 + rect.height() / 2).toFloat(),
                            mTextPaint
                        )
                    } else {
                        c.drawRect(
                            left,
                            view.top - 1f,
                            right,
                            view.top.toFloat(),
                            mPaint
                        )
                    }
                }
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val adapter = parent.adapter
        if (adapter is TestAdapter) {
            val lm = parent.layoutManager as LinearLayoutManager
            val position = lm.findFirstVisibleItemPosition()
            val left = parent.paddingLeft.toFloat()
            val right = parent.width - parent.paddingRight.toFloat()
            val view = parent.findViewHolderForAdapterPosition(position)!!.itemView
            val textY: Float
            val rectTop: Float = parent.paddingTop.toFloat()
            val rectBottom: Float
            val groupName: String = adapter.findPositionByGroupName(position)
            val rect = Rect()
            mTextPaint.getTextBounds(groupName, 0, groupName.length, rect)
            if (position != 0 && adapter.findPositionByGroupName(position)
                != adapter.findPositionByGroupName(position + 1)
            ) {
                rectBottom = headHeight.toFloat().coerceAtMost(view.bottom.toFloat())
                textY = (rectTop + rectBottom - headHeight / 2 + rect.height() / 2)
            } else {
                rectBottom = headHeight.toFloat()
                textY = (rectTop + headHeight / 2 + rect.height() / 2)
            }
            c.drawRect(left, rectTop, right, rectBottom, mPaint)
            c.drawText(groupName, left + 20, textY, mTextPaint)
            // 处理滑动溢出部分
            c.clipRect(
                RectF(
                    left,
                    headHeight + rectTop - (view.height - view.bottom),
                    right,
                    rectTop
                )
            )
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        Log.e("TAG", "getItemOffsets")
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val adapter = parent.adapter
        if (adapter is TestAdapter) {
            if (adapter.isHeadGroup(position)) {
                outRect.set(0, headHeight, 0, 0)
            } else {
                outRect.set(0, 1, 0, 0)
            }
        }
    }
}