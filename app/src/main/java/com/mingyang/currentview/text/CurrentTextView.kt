package com.mingyang.currentview.text

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatTextView

class CurrentTextView : AppCompatTextView {
    private val text: String = "=====文字测试====="
    private var mPaint: Paint
    private var mRedPaint: Paint
    private var mLinePaint: Paint
    private var percentage: Float = 0f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mPaint = Paint()
        mPaint.textSize = dip2px(25).toFloat()
        mPaint.color = Color.BLACK
        mPaint.isAntiAlias = true
        mRedPaint = Paint()
        mRedPaint.textSize = dip2px(25).toFloat()
        mRedPaint.color = Color.RED
        mRedPaint.isAntiAlias = true
        mLinePaint = Paint()
        mLinePaint.color = Color.RED
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.strokeWidth = 5f
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.interpolator = LinearInterpolator()
        animator.duration = 10000
        animator.addUpdateListener {
            percentage = it.animatedValue as Float
            // 重新绘制
            Log.e("TAG", "绘制文字 ${it.animatedValue}")
            invalidate()
        }
        animator.start()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            val measuredHeight = measuredHeight
            val measuredWidth = measuredWidth
            this.drawLine(
                measuredWidth / 2f,
                0f,
                measuredWidth / 2f,
                measuredHeight.toFloat(),
                mLinePaint
            )

            this.drawLine(
                0f,
                measuredHeight / 2f,
                measuredWidth.toFloat(),
                measuredHeight / 2f,
                mLinePaint
            )
            val rect = Rect()
            mPaint.getTextBounds(text, 0, text.length, rect)
            // 偏移量
            val offsetx = (rect.left + rect.right) / 2
            val offsety = (rect.top + rect.bottom) / 2
            val textWidth = mPaint.measureText(text)
            canvas.save()
            clipRect(
                measuredWidth / 2f - textWidth / 2f,
                0f,
                measuredWidth / 2f - textWidth / 2f + textWidth * percentage,
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
                measuredWidth / 2f - textWidth / 2f + textWidth * percentage,
                0f,
                measuredWidth / 2f - textWidth / 2f + textWidth,
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