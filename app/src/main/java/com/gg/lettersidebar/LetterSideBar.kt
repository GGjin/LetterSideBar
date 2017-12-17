package com.gg.lettersidebar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

/**
 * Creator : GG
 * Time    : 2017/12/17
 * Mail    : gg.jin.yu@gmail.com
 * Explain :
 */
class LetterSideBar : View {


    private val mLetterList: Array<String> by lazy {
        arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", "#")
    }

    private val mPaint: Paint  by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.BLUE
            textSize = sp2px(12)
        }
    }

    private fun sp2px(i: Int): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, i.toFloat(), resources.displayMetrics)

    private val mChoosePaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.RED
            textSize = sp2px(12)
        }
    }

    private var mCurrentLetter: String = "A"

    private var mCurrentTouch = false

    private var mTouchLetterListener: OnTouchLetterListener? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = left + right + mPaint.measureText("A").toInt()
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }


    override fun onDraw(canvas: Canvas?) {
        mLetterList.forEachIndexed { index, s ->
            val x = width / 2 - mPaint.measureText(s) / 2
            mPaint.fontMetricsInt.bottom
            val baseLine = height / mLetterList.size + height / mLetterList.size * index - mPaint.fontMetrics.bottom / 2 - mPaint.fontMetrics.top / 2
            if (s == mCurrentLetter) {
                canvas?.drawText(s, x, baseLine, mChoosePaint)
            } else {
                canvas?.drawText(s, x, baseLine, mPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.w("y",event?.y!!.toString())
        val touchPosition = (event?.y!! / height * mLetterList.size).toInt()
        val mOldLetter = mCurrentLetter
        if (touchPosition !in 0..mLetterList.size) {
            mCurrentTouch = false
            touchLetterListener()
            return true
        }
        mCurrentLetter = mLetterList[touchPosition]
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                mCurrentTouch = false
                invalidate()
                touchLetterListener()
            }
            else -> {
                mCurrentTouch = true
                if (mCurrentLetter != mOldLetter) {
                    invalidate()
                    touchLetterListener()
                }
            }
        }

//        when (event?.action) {
//            MotionEvent.ACTION_DOWN,
//            MotionEvent.ACTION_MOVE -> {
//                var index = x / (height / 27)
//                if (index < 0)
//                    index = 0f
//                if (index > 27)
//                    index = 27f
//
//                mCurrentLetter = mLetterList[index.toInt()]
//                Log.w("letter", mCurrentLetter)
//                Log.w("y", x.toString())
//                Log.w("index", index.toString())
//
//                invalidate()
//            }
//        }


        return true
    }


    private fun touchLetterListener() {
        if (mTouchLetterListener != null) {
            mTouchLetterListener?.onTouchLetterChange(mCurrentLetter, mCurrentTouch)
        }
    }

    fun setOnTouchLetterListener(listener: OnTouchLetterListener) {
        mTouchLetterListener = listener
    }

    interface OnTouchLetterListener {
        fun onTouchLetterChange(letter: String, isTouch: Boolean)
    }
}