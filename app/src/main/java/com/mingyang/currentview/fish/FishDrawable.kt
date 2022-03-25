package com.mingyang.currentview.fish

import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.cos
import kotlin.math.sin

class FishDrawable : Drawable() {
    private val mPath: Path = Path()
    private val mPaint: Paint = Paint()

    private val OTHER_ALPHA = 110
    private val BODY_ALPHA = 160


    private var middlePoint: PointF    // 重心点
    private var fishMainAngle: Float = 90f    // 角度

    private val HEAD_RADIUS: Float = 100f    // 头半径
    private val BODY_LENGTH: Float = HEAD_RADIUS * 3.2f // 身长
    private val FIND_FINS_LENGTH: Float = HEAD_RADIUS * 0.9f // 起始位置与头的距离
    private val FINS_LENGTH: Float = HEAD_RADIUS * 1.3f // 鳍长度
    private val BIG_CIRCLE_RADIUS: Float = HEAD_RADIUS * 0.7f // 大圆半径
    private val MIDDLE_CIRCLE_RADIUS: Float = BIG_CIRCLE_RADIUS * 0.6f // 中圆半径
    private val SMALL_CIRCLE_RADIUS: Float = MIDDLE_CIRCLE_RADIUS * 0.4f // 小圆半径
    private val FIND_MIDDLE_CIRCLE_LENGTH: Float = BIG_CIRCLE_RADIUS * (1 + 0.6f) //
    private val FIND_SMALL_CIRCLE_LENGTH: Float = MIDDLE_CIRCLE_RADIUS * (0.3f + 2.7f)
    private val FIND_TRIANGLE_LENGTH: Float = FIND_SMALL_CIRCLE_LENGTH


    init {
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.setARGB(OTHER_ALPHA, 224, 92, 71)
        middlePoint = PointF(4.18f * HEAD_RADIUS, 4.18f * HEAD_RADIUS)

//        val animator = ValueAnimator.ofFloat(360f)
//        animator.repeatMode = ValueAnimator.RESTART
//        animator.currentPlayTime = 8000
//        animator.repeatCount = Int.MAX_VALUE
    }

    /**
     * 总长 8.38
     * 重心 4.18
     * 1.6 +  0.7 * 1.6 + 0.6 *
     */
    override fun draw(canvas: Canvas) {
        // 角度
        val fishAngle = fishMainAngle

        // TODO 绘制头部
        val headPoint = calculatePoint(middlePoint, BODY_LENGTH / 2, fishAngle)
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint)

        // TODO 绘制鳍
        // 绘制右鱼鳍
        val rightFinsStartPoint =
            calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110) // 拿到鳍开始点
        makeFins(canvas, rightFinsStartPoint, fishAngle, true)
        val leftFinsStartPoint =
            calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle + 110) // 拿到鳍开始点
        makeFins(canvas, leftFinsStartPoint, fishAngle, false)
        // 绘制尾部
        val bodyBottomCenterPoint = calculatePoint(headPoint, BODY_LENGTH, fishAngle - 180)
        val middleCenterPoint = makeSegment(
            canvas,
            bodyBottomCenterPoint,
            BIG_CIRCLE_RADIUS,
            MIDDLE_CIRCLE_RADIUS,
            FIND_MIDDLE_CIRCLE_LENGTH,
            fishAngle,
            true
        )
        makeSegment(
            canvas,
            middleCenterPoint,
            MIDDLE_CIRCLE_RADIUS,
            SMALL_CIRCLE_RADIUS,
            FIND_SMALL_CIRCLE_LENGTH,
            fishAngle,
            false
        )
        // 绘制尾巴
        makeTriangel(canvas, middleCenterPoint, FIND_TRIANGLE_LENGTH, BIG_CIRCLE_RADIUS, fishAngle)
        makeTriangel(
            canvas,
            middleCenterPoint,
            FIND_TRIANGLE_LENGTH - 10,
            BIG_CIRCLE_RADIUS - 20,
            fishAngle
        )
        // 绘制身体
        makeBody(canvas, headPoint, bodyBottomCenterPoint, fishAngle)
    }

    /**
     * 绘制身体
     */
    private fun makeBody(
        canvas: Canvas,
        headPoint: PointF,
        bodyBottomPointF: PointF,
        fishAngle: Float
    ) {
        val headRightPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle - 90)
        val headLeftPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle + 90)
        val bottomRightPoint = calculatePoint(bodyBottomPointF, BIG_CIRCLE_RADIUS, fishAngle - 90)
        val bottomLeftPoint = calculatePoint(bodyBottomPointF, BIG_CIRCLE_RADIUS, fishAngle + 90)

        val controlLeftPoint = calculatePoint(headPoint, BODY_LENGTH * 0.56f, fishAngle + 130)
        val controlRightPoint = calculatePoint(headPoint, BODY_LENGTH * 0.56f, fishAngle - 130)

        mPath.reset()
        mPath.moveTo(headLeftPoint.x, headLeftPoint.y)
        mPath.quadTo(controlLeftPoint.x, controlLeftPoint.y, bottomLeftPoint.x, bottomLeftPoint.y)
        mPath.lineTo(bottomLeftPoint.x, bottomLeftPoint.y)
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y)
        mPath.quadTo(controlRightPoint.x, controlRightPoint.y, headRightPoint.x, headRightPoint.y)
        mPaint.alpha = BODY_ALPHA
        canvas.drawPath(mPath, mPaint)
    }

    /**
     * 绘制尾巴
     */
    private fun makeTriangel(
        canvas: Canvas,
        point: PointF,
        centerLength: Float,
        edgeLength: Float,
        fishAngle: Float
    ) {
        val centerPoint = calculatePoint(point, centerLength, fishAngle - 180)
        val topPoint = calculatePoint(centerPoint, edgeLength, fishAngle + 90)
        val bottomPoint = calculatePoint(centerPoint, edgeLength, fishAngle - 90)
        mPath.reset()
        mPath.moveTo(point.x, point.y)
        mPath.lineTo(topPoint.x, topPoint.y)
        mPath.lineTo(bottomPoint.x, bottomPoint.y)
        canvas.drawPath(mPath, mPaint)
    }

    /**
     * 绘制尾部
     */
    private fun makeSegment(
        canvas: Canvas, startPoint: PointF,
        bigCircleRadius: Float, middleCircleRadius: Float, circleSpacing: Float,
        fishAngle: Float, isDrawTopCircle: Boolean
    ): PointF {
        val middleCenterPoint = calculatePoint(startPoint, circleSpacing, fishAngle - 180)
        val bigTopPoint = calculatePoint(startPoint, bigCircleRadius, fishAngle + 90)
        val bigBottomPoint = calculatePoint(startPoint, bigCircleRadius, fishAngle - 90)
        val middleTopPoint =
            calculatePoint(middleCenterPoint, middleCircleRadius, fishAngle + 90)
        val middleBottomPoint =
            calculatePoint(middleCenterPoint, middleCircleRadius, fishAngle - 90)

        mPath.reset()
        mPath.moveTo(bigTopPoint.x, bigTopPoint.y)
        mPath.lineTo(bigBottomPoint.x, bigBottomPoint.y)
        mPath.lineTo(middleBottomPoint.x, middleBottomPoint.y)
        mPath.lineTo(middleTopPoint.x, middleTopPoint.y)
        canvas.drawPath(mPath, mPaint)
        if (isDrawTopCircle)
            canvas.drawCircle(startPoint.x, startPoint.y, bigCircleRadius, mPaint)
        canvas.drawCircle(middleCenterPoint.x, middleCenterPoint.y, middleCircleRadius, mPaint)
        return middleCenterPoint
    }

    /**
     * 绘制鱼鳍
     */
    private fun makeFins(canvas: Canvas, startPoint: PointF, fishAngle: Float, isRight: Boolean) {
        val controlAngle = 115 // 控制点角度
        val rightFinsEndPoint =
            calculatePoint(startPoint, FINS_LENGTH, fishAngle - 180) // 拿到鳍结束点 TODO 注意
        val controlPoint =
            calculatePoint(
                startPoint, FINS_LENGTH * 1.8f,
                if (isRight)
                    fishAngle - controlAngle
                else
                    fishAngle + controlAngle
            )// 控制点
        mPath.reset()         // 绘制
        mPath.moveTo(startPoint.x, startPoint.y) // 移动到起始点
        mPath.quadTo(
            controlPoint.x,
            controlPoint.y,
            rightFinsEndPoint.x,
            rightFinsEndPoint.y
        ) // 绘制二阶贝塞尔曲线：第一个为控制点 ，第二个为终点
        canvas.drawPath(mPath, mPaint)
    }

    /**
     * 计算坐标
     * angle 角度
     * startPoint 起始位置
     * length 长度（斜边）
     * sin angle = y/length ==> y = sin angle * length
     * cos angle = x/length ==> x = cos angle * length
     * 需要将 angle 角度进行转换  Math.toRadians() 将角度转换为弧度 360° = 2π
     * 注意：
     *  Android中y轴向下为正，向上为负，
     *  计算出来的坐标，是参照其实位置
     */
    private fun calculatePoint(startPoint: PointF, length: Float, angle: Float): PointF {
        val x: Float = (cos(Math.toRadians(angle.toDouble())) * length).toFloat()
        val y: Float = (sin(Math.toRadians(angle.toDouble() - 180)) * length).toFloat()
        return PointF(startPoint.x + x, startPoint.y + y)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun getIntrinsicHeight(): Int = (8.38f * HEAD_RADIUS).toInt()

    override fun getIntrinsicWidth(): Int = (8.38f * HEAD_RADIUS).toInt()
}