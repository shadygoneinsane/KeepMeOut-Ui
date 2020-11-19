package com.example.keepmeout_ui.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.example.keepmeout_ui.R
import github.hellocsl.cursorwheel.CursorWheelLayout

/**
 *
 */
class SimpleTextCursorWheelLayout(context: Context, attrs: AttributeSet?) : CursorWheelLayout(context, attrs) {
    override fun onInnerItemSelected(v: View?) {
        super.onInnerItemSelected(v)
        val tv = v?.findViewById<View>(R.id.wheel_menu_item_tv)
        tv?.animate()?.scaleX(1.4f)?.scaleY(1.4f)
    }

    override fun onInnerItemUnselected(v: View?) {
        super.onInnerItemUnselected(v)
        val tv = v?.findViewById<View>(R.id.wheel_menu_item_tv)
        tv?.animate()?.scaleX(1f)?.scaleY(1f)
    }
}