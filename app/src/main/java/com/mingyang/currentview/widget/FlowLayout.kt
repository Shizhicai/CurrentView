package com.mingyang.currentview.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import java.util.*

/**
 * 自定义 FlowLayout
 */
class FlowLayout : ViewGroup {
    private var lineViews: ArrayList<ArrayList<View>> = ArrayList()
    private var verticalSize = 20
    private var horizontalSize = 30

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)

    constructor(context: Context, attr: AttributeSet?, style: Int) : super(context, attr, style)

    private fun clearView() {
        lineViews.clear()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        clearView()
        // 父容器分配的宽高
        val flowHeight: Int = MeasureSpec.getSize(heightMeasureSpec)
        val flowWidth: Int = MeasureSpec.getSize(widthMeasureSpec)
        // 获取宽高设置的模式
        val flowHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        val flowWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        var measureHeight = 0
        var measureWidth = 0
        var lineView: ArrayList<View> = ArrayList()
        // 循环进行遍历子view
        for (position in 0 until childCount) {
            val childView = getChildAt(position)
            if(childView.visibility != View.GONE){
                val layoutParams = childView.layoutParams
                // 获取子类 MeasureSpec 的模式
                val childMeasureWidthSpec =
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, layoutParams.width)
                val childMeasureHeightSpec =
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, layoutParams.height)
                // childView 自己去测量大小
                childView.measure(childMeasureWidthSpec, childMeasureHeightSpec)
                // 获取测量后结果
                val childMeasureHeight = childView.measuredHeight
                val childMeasureWidth = childView.measuredWidth
                // 若累加宽度大于当前容器宽度，进行换行
                if (measureWidth + horizontalSize + childMeasureWidth > flowWidth) {
                    lineViews.add(lineView)
                    lineView = ArrayList()
                    measureWidth = childMeasureWidth
                    measureHeight += childMeasureHeight + verticalSize
                } else {
                    // 若是累计宽度不大于当前容器
                    measureWidth += childMeasureWidth + horizontalSize
                    measureHeight = Math.max(childMeasureHeight, measureHeight)
                }
                lineView.add(childView)
                if (position == childCount - 1 && lineView.size > 0) {
                    lineViews.add(lineView)
                }
            }
        }
        measureHeight =
            if (flowHeightMode == MeasureSpec.EXACTLY) flowHeight else (measureHeight + paddingTop + paddingBottom)
        measureWidth = if (flowWidthMode == MeasureSpec.EXACTLY) flowWidth else measureWidth
        setMeasuredDimension(measureWidth, measureHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var lineCountHeight = paddingTop
        for (views in lineViews) {
            var lineWidth = paddingLeft
            var lineHeight = paddingTop
            for (view in views) {
                if(view.visibility != View.GONE) {
                    view.layout(
                        lineWidth,
                        lineCountHeight,
                        view.measuredWidth + lineWidth,
                        view.measuredHeight + lineCountHeight
                    )
                    lineWidth += view.measuredWidth + horizontalSize
                    lineHeight = Math.max(view.measuredHeight, lineHeight)
                }
            }
            lineCountHeight += if (lineViews.indexOf(views) != lineViews.size - 1) {
                verticalSize + lineHeight
            } else {
                lineHeight
            }
        }
    }

}