package com.button.toggle.widget

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import com.button.toggle.R
import com.button.toggle.model.ToggleableView

class LabeledSwitch : ToggleableView {
    private var padding = 0
    private var colorOn = 0
    private var colorOff = 0
    private var colorBorder = 0
    private var colorDisabled = 0
    private var textSize = 0
    private var outerRadii = 0
    private var thumbRadii = 0
    private lateinit var paint: Paint
    private var startTime: Long = 0
    private lateinit var labelOn: String
    private lateinit var labelOff: String
    private lateinit var thumbBounds: RectF
    private lateinit var leftBgArc: RectF
    private lateinit var rightBgArc: RectF
    private lateinit var leftFgArc: RectF
    private lateinit var rightFgArc: RectF

    private lateinit var outlineFgArc: RectF
    private lateinit var strokePaint: Paint
    private var hasTransparentBackground: Boolean = false

    private var typeface: Typeface? = null
    private var thumbOnCenterX = 0f
    private var thumbOffCenterX = 0f

    /**
     * Simple constructor to use when creating a switch from code.
     *
     * @param context The Context the switch is running in, through which it can
     * access the current theme, resources, etc.
     */
    constructor(context: Context) : super(context) {
        initView()
    }

    /**
     * Constructor that is called when inflating a switch from XML.
     *
     * @param context The Context the switch is running in, through which it can
     * access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the switch.
     */
    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
        initView()
        initProperties(attrs)
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute.
     *
     * @param context      The Context the switch is running in, through which it can
     * access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the switch.
     * @param defStyleAttr An attribute in the current theme that contains a
     * reference to a style resource that supplies default values for
     * the switch. Can be 0 to not look for defaults.
     */
    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
        initProperties(attrs)
    }

    private fun initView() {
        isOn = false
        labelOn = "ON"
        labelOff = "OFF"
        enabled = true
        textSize = (12f * resources.displayMetrics.scaledDensity).toInt()
        colorOn = ContextCompat.getColor(context, R.color.colorAccent)
        colorBorder = colorOn
        paint = Paint()
        paint.isAntiAlias = true
        leftBgArc = RectF()
        rightBgArc = RectF()
        leftFgArc = RectF()
        rightFgArc = RectF()
        thumbBounds = RectF()
        outlineFgArc = RectF()
        colorOff = Color.parseColor("#FFFFFF")
        colorDisabled = Color.parseColor("#D3D3D3")
        strokePaint = Paint().apply {
            color = colorBorder
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
    }

    private fun initProperties(attrs: AttributeSet) {
        val attr: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.Toggle, 0, 0)
        for (i in 0 until attr.indexCount) {
            when (attr.getIndex(i)) {
                R.styleable.Toggle_noBackground -> {
                    hasTransparentBackground =
                        attr.getBoolean(R.styleable.Toggle_noBackground, false)
                }
                R.styleable.Toggle_on -> {
                    isOn = attr.getBoolean(R.styleable.Toggle_on, false)
                }
                R.styleable.Toggle_colorOff -> {
                    colorOff =
                        attr.getColor(R.styleable.Toggle_colorOff, Color.parseColor("#FFFFFF"))
                }
                R.styleable.Toggle_colorBorder -> {
                    val accentColor: Int = ContextCompat.getColor(context, R.color.colorAccent)
                    colorBorder = attr.getColor(R.styleable.Toggle_colorBorder, accentColor)
                    strokePaint.color = colorBorder
                }
                R.styleable.Toggle_colorOn -> {
                    val accentColor: Int = ContextCompat.getColor(context, R.color.colorAccent)
                    colorOn = attr.getColor(R.styleable.Toggle_colorOn, accentColor)
                }
                R.styleable.Toggle_colorDisabled -> {
                    colorDisabled =
                        attr.getColor(R.styleable.Toggle_colorOff, Color.parseColor("#D3D3D3"))
                }
                R.styleable.Toggle_textOff -> {
                    labelOff = attr.getString(R.styleable.Toggle_textOff)!!
                }
                R.styleable.Toggle_textOn -> {
                    labelOn = attr.getString(R.styleable.Toggle_textOn)!!
                }
                R.styleable.Toggle_android_textSize -> {
                    val defaultTextSize = (12f * resources.displayMetrics.scaledDensity).toInt()
                    textSize =
                        attr.getDimensionPixelSize(
                            R.styleable.Toggle_android_textSize,
                            defaultTextSize
                        )
                }
                R.styleable.Toggle_android_enabled -> {
                    enabled = attr.getBoolean(R.styleable.Toggle_android_enabled, false)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.textSize = textSize.toFloat()

        //Drawing Switch background here
        drawSwitchBackground(canvas, paint)

        //Drawing Switch Labels here
        drawSwitchLabels(canvas, paint)

        //Drawing Switch Thumb here
        drawSwitchThumb(canvas, paint)
    }

    private fun drawSwitchBackground(canvas: Canvas, paint: Paint) {
        //for center part of rectangle
        if (isEnabled) {
            paint.color = colorBorder
        } else {
            paint.color = colorDisabled
        }
        if (!hasTransparentBackground) {
            //draws left and right part of wedge
            canvas.drawArc(leftBgArc, 90f, 180f, false, paint)
            canvas.drawArc(rightBgArc, 90f, -180f, false, paint)

            //for center rectangle part of switch/view
            canvas.drawRect(
                outerRadii.toFloat(), 0f,
                (width - outerRadii).toFloat(), height.toFloat(), paint
            )

            paint.color = colorOff
            canvas.drawArc(leftFgArc, 90f, 180f, false, paint)
            canvas.drawArc(rightFgArc, 90f, -180f, false, paint)
            canvas.drawRect(
                outerRadii.toFloat(), (padding / 10).toFloat(),
                (width - outerRadii).toFloat(), (height - padding / 10).toFloat(), paint
            )
        }

        //For drawing for turned on state
        var alpha =
            ((thumbBounds.centerX() - thumbOffCenterX) / (thumbOnCenterX - thumbOffCenterX) * 255).toInt()
        alpha = if (alpha < 0) 0 else alpha.coerceAtMost(255)
        val onColor: Int = if (isEnabled) {
            Color.argb(alpha, Color.red(colorOn), Color.green(colorOn), Color.blue(colorOn))
        } else {
            Color.argb(
                alpha, Color.red(colorDisabled),
                Color.green(colorDisabled), Color.blue(colorDisabled)
            )
        }
        paint.color = onColor
        if (!hasTransparentBackground) {
            //draws left and right part of wedge (when turned on)
            canvas.drawArc(leftBgArc, 90f, 180f, false, paint)
            canvas.drawArc(rightBgArc, 90f, -180f, false, paint)

            //draws background when switch is turned on
            canvas.drawRect(
                outerRadii.toFloat(), 0f,
                (width - outerRadii).toFloat(), height.toFloat(), paint
            )
        }

        //For drawing for turned off state
        alpha =
            ((thumbOnCenterX - thumbBounds.centerX()) / (thumbOnCenterX - thumbOffCenterX) * 255).toInt()
        alpha = if (alpha < 0) 0 else alpha.coerceAtMost(255)
        val offColor =
            Color.argb(alpha, Color.red(colorOff), Color.green(colorOff), Color.blue(colorOff))
        paint.color = offColor

        if (!hasTransparentBackground) {
            //draws left and right part of wedge (when turned off)
            canvas.drawArc(leftFgArc, 90f, 180f, false, paint)
            canvas.drawArc(rightFgArc, 90f, -180f, false, paint)

            //draws background when switch is turned off
            canvas.drawRect(
                outerRadii.toFloat(), (padding / 10).toFloat(),
                (width - outerRadii).toFloat(), (height - padding / 10).toFloat(), paint
            )
        } else {
            canvas.drawRoundRect(
                outlineFgArc, outerRadii.toFloat(),
                outerRadii.toFloat(), strokePaint
            )
        }
    }

    private fun drawSwitchLabels(canvas: Canvas, paint: Paint) {
        val maxChar = "N"
        val textCenter = paint.measureText(maxChar) / 2
        if (isOn) {
            var alpha =
                (((width ushr 1) - thumbBounds.centerX()) / ((width ushr 1) - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else alpha.coerceAtMost(255)
            val onColor =
                Color.argb(alpha, Color.red(colorOn), Color.green(colorOn), Color.blue(colorOn))
            paint.color = onColor
            var centerX =
                (width - padding - (padding + (padding ushr 1) + (thumbRadii shl 1)) ushr 1).toFloat()
            canvas.drawText(
                labelOff,
                padding + (padding ushr 1) + (thumbRadii shl 1) + centerX - paint.measureText(
                    labelOff
                ) / 2,
                (height ushr 1) + textCenter,
                paint
            )
            alpha =
                ((thumbBounds.centerX() - (width ushr 1)) / (thumbOnCenterX - (width ushr 1)) * 255).toInt()
            alpha = if (alpha < 0) 0 else alpha.coerceAtMost(255)
            val offColor =
                Color.argb(alpha, Color.red(colorOff), Color.green(colorOff), Color.blue(colorOff))
            paint.color = offColor
            val maxSize = width - (padding shl 1) - (thumbRadii shl 1)
            centerX = ((padding ushr 1) + maxSize - padding ushr 1).toFloat()
            canvas.drawText(
                labelOn,
                padding + centerX - paint.measureText(labelOn) / 2,
                (height ushr 1) + textCenter,
                paint
            )
        } else {
            var alpha =
                ((thumbBounds.centerX() - (width ushr 1)) / (thumbOnCenterX - (width ushr 1)) * 255).toInt()
            alpha = if (alpha < 0) 0 else alpha.coerceAtMost(255)
            val offColor =
                Color.argb(alpha, Color.red(colorOff), Color.green(colorOff), Color.blue(colorOff))
            paint.color = offColor
            val maxSize = width - (padding shl 1) - (thumbRadii shl 1)
            var centerX = ((padding ushr 1) + maxSize - padding ushr 1).toFloat()
            canvas.drawText(
                labelOn,
                padding + centerX - paint.measureText(labelOn) / 2,
                (height ushr 1) + textCenter,
                paint
            )
            alpha =
                (((width ushr 1) - thumbBounds.centerX()) / ((width ushr 1) - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else alpha.coerceAtMost(255)
            val onColor: Int
            onColor = if (isEnabled) {
                Color.argb(alpha, Color.red(colorOn), Color.green(colorOn), Color.blue(colorOn))
            } else {
                Color.argb(
                    alpha, Color.red(colorDisabled),
                    Color.green(colorDisabled), Color.blue(colorDisabled)
                )
            }
            paint.color = onColor
            centerX =
                (width - padding - (padding + (padding ushr 1) + (thumbRadii shl 1)) ushr 1).toFloat()
            canvas.drawText(
                labelOff,
                padding + (padding ushr 1) + (thumbRadii shl 1) + centerX - paint.measureText(
                    labelOff
                ) / 2,
                (height ushr 1) + textCenter,
                paint
            )
        }
    }

    private fun drawSwitchThumb(canvas: Canvas, paint: Paint) {
        var alpha =
            ((thumbBounds.centerX() - thumbOffCenterX) / (thumbOnCenterX - thumbOffCenterX) * 255).toInt()
        alpha = if (alpha < 0) 0 else alpha.coerceAtMost(255)
        val offColor =
            Color.argb(alpha, Color.red(colorOff), Color.green(colorOff), Color.blue(colorOff))
        paint.color = offColor
        canvas.drawCircle(
            thumbBounds.centerX(), thumbBounds.centerY(),
            thumbRadii.toFloat(), paint
        )
        alpha =
            ((thumbOnCenterX - thumbBounds.centerX()) / (thumbOnCenterX - thumbOffCenterX) * 255).toInt()
        alpha = if (alpha < 0) 0 else alpha.coerceAtMost(255)
        val onColor: Int
        onColor = if (isEnabled) {
            Color.argb(alpha, Color.red(colorOn), Color.green(colorOn), Color.blue(colorOn))
        } else {
            Color.argb(
                alpha, Color.red(colorDisabled),
                Color.green(colorDisabled), Color.blue(colorDisabled)
            )
        }
        paint.color = onColor
        canvas.drawCircle(
            thumbBounds.centerX(), thumbBounds.centerY(),
            thumbRadii.toFloat(), paint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = resources.getDimensionPixelSize(R.dimen.labeled_default_width)
        val desiredHeight = resources.getDimensionPixelSize(R.dimen.labeled_default_height)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> desiredWidth.coerceAtMost(widthSize)
            else -> desiredWidth
        }

        height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> desiredHeight.coerceAtMost(heightSize)
            else -> desiredHeight
        }

        setMeasuredDimension(width, height)
        outerRadii = width.coerceAtMost(height) ushr 1
        thumbRadii = (width.coerceAtMost(height) / 2.88f).toInt()
        padding = height - thumbRadii ushr 1
        thumbBounds[(width - padding - thumbRadii).toFloat(), padding.toFloat(), (width - padding).toFloat()] =
            (height - padding).toFloat()
        thumbOnCenterX = thumbBounds.centerX()
        thumbBounds[padding.toFloat(), padding.toFloat(), (padding + thumbRadii).toFloat()] =
            (height - padding).toFloat()
        thumbOffCenterX = thumbBounds.centerX()
        if (isOn) {
            thumbBounds[(width - padding - thumbRadii).toFloat(), padding.toFloat(), (width - padding).toFloat()] =
                (height - padding).toFloat()
        } else {
            thumbBounds[padding.toFloat(), padding.toFloat(), (padding + thumbRadii).toFloat()] =
                (height - padding).toFloat()
        }
        leftBgArc[0f, 0f, (outerRadii shl 1).toFloat()] = height.toFloat()
        rightBgArc[(width - (outerRadii shl 1)).toFloat(), 0f, width.toFloat()] = height.toFloat()
        leftFgArc[(padding / 10).toFloat(), (padding / 10).toFloat(), ((outerRadii shl 1) - padding / 10).toFloat()] =
            (height - padding / 10).toFloat()
        rightFgArc[(width - (outerRadii shl 1) + padding / 10).toFloat(), (padding / 10).toFloat(), (width - padding / 10).toFloat()] =
            (height - padding / 10).toFloat()

        outlineFgArc[(padding / 10).toFloat(), (padding / 10).toFloat(), (width - padding / 10).toFloat()] =
            (height - padding / 10).toFloat()
    }

    /**
     * Call this view's OnClickListener, if it is defined.  Performs all normal
     * actions associated with clicking: reporting accessibility event, playing
     * a sound, etc.
     *
     * @return True there was an assigned OnClickListener that was called, false
     * otherwise is returned.
     */
    override fun performClick(): Boolean {
        super.performClick()
        if (isOn) {
            val switchColor =
                ValueAnimator.ofFloat((width - padding - thumbRadii).toFloat(), padding.toFloat())
            switchColor.addUpdateListener { animation: ValueAnimator ->
                val value = animation.animatedValue as Float
                thumbBounds[value, thumbBounds.top, value + thumbRadii] = thumbBounds.bottom
                invalidate()
            }
            switchColor.interpolator = AccelerateDecelerateInterpolator()
            switchColor.duration = 250
            switchColor.start()
        } else {
            val switchColor =
                ValueAnimator.ofFloat(padding.toFloat(), (width - padding - thumbRadii).toFloat())
            switchColor.addUpdateListener { animation: ValueAnimator ->
                val value = animation.animatedValue as Float
                thumbBounds[value, thumbBounds.top, value + thumbRadii] = thumbBounds.bottom
                invalidate()
            }
            switchColor.interpolator = AccelerateDecelerateInterpolator()
            switchColor.duration = 250
            switchColor.start()
        }
        isOn = !isOn
        if (onToggledListener != null) {
            onToggledListener.onSwitched(this, isOn)
        }
        return true
    }

    /**
     * Method to handle touch screen motion events.
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (isEnabled) {
            val x = event.x
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startTime = System.currentTimeMillis()
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (x - (thumbRadii ushr 1) > padding && x + (thumbRadii ushr 1) < width - padding) {
                        thumbBounds[x - (thumbRadii ushr 1), thumbBounds.top, x + (thumbRadii ushr 1)] =
                            thumbBounds.bottom
                        invalidate()
                    }
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val endTime = System.currentTimeMillis()
                    val span = endTime - startTime
                    if (span < 200) {
                        performClick()
                    } else {
                        if (x >= width ushr 1) {
                            val switchColor = ValueAnimator.ofFloat(
                                if (x > (width - padding - thumbRadii))
                                    (width - padding - thumbRadii).toFloat()
                                else x, (width - padding - thumbRadii).toFloat()
                            )
                            switchColor.addUpdateListener { animation: ValueAnimator ->
                                val value = animation.animatedValue as Float
                                thumbBounds[value, thumbBounds.top, value + thumbRadii] =
                                    thumbBounds.bottom
                                invalidate()
                            }
                            switchColor.interpolator = AccelerateDecelerateInterpolator()
                            switchColor.duration = 250
                            switchColor.start()
                            isOn = true
                        } else {
                            val switchColor = ValueAnimator.ofFloat(
                                if (x < padding) padding.toFloat() else x,
                                padding.toFloat()
                            )
                            switchColor.addUpdateListener { animation: ValueAnimator ->
                                val value = animation.animatedValue as Float
                                thumbBounds[value, thumbBounds.top, value + thumbRadii] =
                                    thumbBounds.bottom
                                invalidate()
                            }
                            switchColor.interpolator = AccelerateDecelerateInterpolator()
                            switchColor.duration = 250
                            switchColor.start()
                            isOn = false
                        }
                        if (onToggledListener != null) {
                            onToggledListener.onSwitched(this, isOn)
                        }
                    }
                    invalidate()
                    true
                }
                else -> super.onTouchEvent(event)
            }
        } else {
            false
        }
    }

    /**
     *
     * Returns the color value for colorOn.
     *
     * @return color value for label and thumb in off state and background in on state.
     */
    fun getColorOn(): Int {
        return colorOn
    }

    /**
     *
     * Changes the on color value of this Switch.
     *
     * @param colorOn color value for label and thumb in off state and background in on state.
     */
    fun setColorOn(colorOn: Int) {
        this.colorOn = colorOn
        invalidate()
    }

    /**
     *
     * Returns the color value for colorOff.
     *
     * @return color value for label and thumb in on state and background in off state.
     */
    fun getColorOff(): Int {
        return colorOff
    }

    /**
     *
     * Changes the off color value of this Switch.
     *
     * @param colorOff color value for label and thumb in on state and background in off state.
     */
    fun setColorOff(colorOff: Int) {
        this.colorOff = colorOff
        invalidate()
    }

    /**
     *
     * Returns text label when switch is in on state.
     *
     * @return text label when switch is in on state.
     */
    fun getLabelOn(): String? {
        return labelOn
    }

    /**
     *
     * Changes text label when switch is in on state.
     *
     * @param labelOn text label when switch is in on state.
     */
    fun setLabelOn(labelOn: String) {
        this.labelOn = labelOn
        invalidate()
    }

    /**
     *
     * Returns text label when switch is in off state.
     *
     * @return text label when switch is in off state.
     */
    fun getLabelOff(): String {
        return labelOff
    }

    /**
     *
     * Changes text label when switch is in off state.
     *
     * @param labelOff text label when switch is in off state.
     */
    fun setLabelOff(labelOff: String) {
        this.labelOff = labelOff
        invalidate()
    }

    /**
     *
     * Returns the typeface for Switch on/off labels.
     *
     * @return the typeface for Switch on/off labels..
     */
    fun getTypeface(): Typeface? {
        return typeface
    }

    /**
     *
     * Changes the typeface for Switch on/off labels.
     *
     * @param typeface the typeface for Switch on/off labels.
     */
    fun setTypeface(typeface: Typeface?) {
        this.typeface = typeface
        paint.typeface = typeface
        invalidate()
    }

    /**
     *
     * Changes the boolean state of this Switch.
     *
     * @param on true to turn switch on, false to turn it off.
     */
    override fun setOn(on: Boolean) {
        super.setOn(on)
        if (isOn) {
            thumbBounds[(width - padding - thumbRadii).toFloat(), padding.toFloat(), (width - padding).toFloat()] =
                (height - padding).toFloat()
        } else {
            thumbBounds[padding.toFloat(), padding.toFloat(), (padding + thumbRadii).toFloat()] =
                (height - padding).toFloat()
        }
        invalidate()
    }

    /**
     *
     * Returns the color value for Switch disabled state.
     *
     * @return color value used by background, border and thumb when switch is disabled.
     */
    fun getColorDisabled(): Int {
        return colorDisabled
    }

    /**
     *
     * Changes the color value for Switch disabled state.
     *
     * @param colorDisabled color value used by background, border and thumb when switch is disabled.
     */
    fun setColorDisabled(colorDisabled: Int) {
        this.colorDisabled = colorDisabled
        invalidate()
    }

    /**
     *
     * Returns the color value for Switch border.
     *
     * @return color value used by Switch border.
     */
    fun getColorBorder(): Int {
        return colorBorder
    }

    /**
     *
     * Changes the color value for Switch disabled state.
     *
     * @param colorBorder color value used by Switch border.
     */
    fun setColorBorder(colorBorder: Int) {
        this.colorBorder = colorBorder
        invalidate()
    }

    /**
     *
     * Returns the text size for Switch on/off label.
     *
     * @return text size for Switch on/off label.
     */
    fun getTextSize(): Int {
        return textSize
    }

    /**
     *
     * Changes the text size for Switch on/off label.
     *
     * @param textSize text size for Switch on/off label.
     */
    fun setTextSize(textSize: Int) {
        this.textSize = (textSize * resources.displayMetrics.scaledDensity).toInt()
        invalidate()
    }
}