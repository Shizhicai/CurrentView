package com.mingyang.currentview.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.view.ViewCompat

class ScrollTestView : View {
    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var mDragSlop = ViewConfiguration.get(context).scaledTouchSlop

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = event.x - lastX
                val moveY = event.y - lastY
                if (kotlin.math.abs(moveX) > mDragSlop || kotlin.math.abs(moveY) > mDragSlop) {
                    ViewCompat.offsetTopAndBottom(this, moveY.toInt())
                    ViewCompat.offsetLeftAndRight(this, moveX.toInt())
                }
                lastY = event.y
                lastX = event.x
            }
        }
        return true
    }
}