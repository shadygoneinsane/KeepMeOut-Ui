package com.example.keepmeout_ui.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.keepmeout_ui.R
import github.hellocsl.cursorwheel.CursorWheelLayout

/**
 *
 */
class SimpleTextCursorWheelLayout(context: Context, attrs: AttributeSet?) :
    CursorWheelLayout(context, attrs) {
    override fun onInnerItemSelected(v: View?) {
        super.onInnerItemSelected(v)
        val tv = v?.findViewById<TextView>(R.id.wheel_menu_item_tv)
        tv?.animate()?.scaleX(1.6f)?.scaleY(1.6f)
        tv?.setTextColor(ContextCompat.getColor(context, R.color.white))
    }

    override fun onInnerItemUnselected(v: View?) {
        super.onInnerItemUnselected(v)
        val tv = v?.findViewById<TextView>(R.id.wheel_menu_item_tv)
        tv?.animate()?.scaleX(1f)?.scaleY(1f)
        tv?.setTextColor(ContextCompat.getColor(context, R.color.white_dimmer))
    }
}