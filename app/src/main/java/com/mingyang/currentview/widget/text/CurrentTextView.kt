package com.mingyang.currentview.widget.text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CurrentTextView : AppCompatTextView {
    private val text: String = "测试"
    private var mPaint: Paint
    private var mRedPaint: Paint
    private var mLinePaint: Paint
    private var percentage: Float = 0f
    private var leftToRight: Boolean = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mPaint = Paint()
        mPaint.textSize = dip2px(16).toFloat()
        mPaint.color = Color.BLACK
        mPaint.isAntiAlias = true
        mRedPaint = Paint()
        mRedPaint.textSize = dip2px(16).toFloat()
        mRedPaint.color = Color.RED
        mRedPaint.isAntiAlias = true
        mLinePaint = Paint()
        mLinePaint.color = Color.RED
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.strokeWidth = 5f
//        val animator = ValueAnimator.ofFloat(0f, 1f)
//        animator.interpolator = LinearInterpolator()
//        animator.duration = 3000
//        animator.addUpdateListener {
//            percentage = it.animatedValue as Float
//            // 重新绘制
//            Log.e("TAG", "绘制文字 ${it.animatedValue}")
//            invalidate()
//        }
//        animator.start()
    }

    fun setPercentage(percentage: Float, leftToRight: Boolean) {
        this.percentage = percentage
        this.leftToRight = leftToRight
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            val measuredHeight = measuredHeight
            val measuredWidth = measuredWidth
            val rect = Rect()

            mPaint.getTextBounds(text, 0, text.length, rect)
            // 偏移量
            val offsetx = (rect.left + rect.right) / 2
            val offsety = (rect.top + rect.bottom) / 2
            val textWidth = mPaint.measureText(text)
            canvas.save()
            var blackStart: Float
            var blackEnd: Float
            var redStart: Float
            var redEnd: Float
            if (leftToRight) {
                // 从左到右 红-> 黑
                redStart = measuredWidth / 2f - textWidth / 2f
                redEnd = measuredWidth / 2f - textWidth / 2f + textWidth * percentage
                blackStart = redEnd
                blackEnd = measuredWidth / 2f - textWidth / 2f + textWidth
            } else {
                // 从左到右 黑 -> 红
                blackStart = measuredWidth / 2f - textWidth / 2f
                blackEnd = measuredWidth / 2f - textWidth / 2f + textWidth * percentage
                redStart = blackEnd
                redEnd = measuredWidth / 2f - textWidth / 2f + textWidth
            }
            clipRect(
                redStart,
                0f,
                redEnd,
                measuredHeight.toFloat()
            )
            this.drawText(
                text,
                0,
                text.length,
                (measuredWidth / 2 - offsetx).toFloat(),
                (measuredHeight / 2 - offsety).toFloat(),
                mRedPaint
            )
            canvas.restore()
            canvas.save()
            clipRect(
                blackStart,
                0f,
                blackEnd,
                measuredHeight.toFloat()
            )
            this.drawText(
                text,
                0,
                text.length,
                (measuredWidth / 2 - offsetx).toFloat(),
                (measuredHeight / 2 - offsety).toFloat(),
                mPaint
            )
            canvas.restore()
        }
    }

    private fun dip2px(dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue.toFloat() * scale + 0.5f).toInt()
    }
}