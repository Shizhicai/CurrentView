package com.mingyang.currentview.scroll

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView


class NestedScrollLayout : NestedScrollView {
    var contextView: ViewGroup? = null
    var topView: View? = null

    /**
     * 总共滑动的距离
     */
    var totalDy: Int = 0

    /**
     * 记录当前滑动的y轴加速度
     */
    private var velocityY = 0

    /**
     * 用于判断RecyclerView是否在fling
     */
    var isStartFling = false
    private var mFlingHelper: FlingHelper? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)


    constructor(context: Context, attr: AttributeSet?, defStyle: Int) : super(
        context,
        attr,
        defStyle
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            init()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        Log.e("===", "init ===== ")
        mFlingHelper = FlingHelper(context)
        setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (isStartFling) {
                totalDy = 0
                isStartFling = false
            }
            if (scrollY == 0) {
                Log.e("===", "TOP SCROLL")
                // refreshLayout.setEnabled(true);
            }
            if (scrollY == getChildAt(0).measuredHeight - v.measuredHeight) {
                Log.e("===", "BOTTOM SCROLL $velocityY")
                dispatchChildFling()
            }
            //在RecyclerView fling情况下，记录当前RecyclerView在y轴的偏移
            totalDy += scrollY - oldScrollY
        }
    }

    private fun dispatchChildFling() {
        if (velocityY != 0) {
            // 通过速度获取距离
            val splineFlingDistance = mFlingHelper?.getSplineFlingDistance(velocityY)
            Log.e("===", "dispatchChildFling $splineFlingDistance  totalDy:$totalDy")
            if (splineFlingDistance != null && splineFlingDistance > totalDy) {
                mFlingHelper?.getVelocityByDistance(splineFlingDistance - totalDy)
                    ?.let { childFling(it) }
            }
        }
        totalDy = 0
        velocityY = 0
    }

    private fun childFling(velocityByDistance: Int) {
        val rv = getChildRecyclerView(contextView!!)
        if (rv != null) {
            Log.e("===", "子类进行滑动距离")
            rv.fling(0, velocityByDistance)
        }
    }

    private fun getChildRecyclerView(viewGroup: ViewGroup): RecyclerView? {
        for (i in 0 until viewGroup.childCount) {
            val view = viewGroup.getChildAt(i)
            if (view is RecyclerView && view::class.java == NestedLogRecyclerView::class.java) {
                return viewGroup.getChildAt(i) as RecyclerView
            } else if (viewGroup.getChildAt(i) is ViewGroup) {
                val childRecyclerView: ViewGroup? =
                    getChildRecyclerView(viewGroup.getChildAt(i) as ViewGroup)
                if (childRecyclerView is RecyclerView) {
                    return childRecyclerView
                }
            }
            continue
        }
        return null
    }


    /**
     * 获取速度
     */
    override fun fling(velocityY: Int) {
        super.fling(velocityY)
        if (velocityY <= 0) {
            this.velocityY = 0
        } else {
            isStartFling = true
            this.velocityY = velocityY
        }
        Log.e("===", "速度===$velocityY")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        topView = (getChildAt(0) as ViewGroup).getChildAt(0)
        contextView = (getChildAt(0) as ViewGroup).getChildAt(1) as ViewGroup
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        /**
         * 将父容器的高度设置到底部容器，就可以将tabLayout固定住
         */
        val measuredHeight = measuredHeight
        val layoutParams = contextView?.layoutParams
        layoutParams?.height = measuredHeight
        contextView?.layoutParams = layoutParams
    }

    /**
     * 此处是由父容器进行消耗
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(target, dx, dy, consumed, type)
        val hintTop = dy > 0 && scrollY < topView!!.measuredHeight
        if (hintTop) {
            scrollBy(0, dy)
            // 消耗滑动距离，若
            consumed[1] = dy
        }
    }


}