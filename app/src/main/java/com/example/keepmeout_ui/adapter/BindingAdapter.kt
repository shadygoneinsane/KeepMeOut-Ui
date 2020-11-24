package com.example.keepmeout_ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.keepmeout_ui.R


/**
 * Custom binding adapter
 *
 * Created by: Vikesh Dass
 * Created on: 24-11-2020
 * Email : vikeshdass@gmail.com
 */
@BindingAdapter("isEnabled")
fun View.setViewState(isEnabled: Boolean) {
    (this as? TextView)?.setTextColor(
        ContextCompat.getColor(
            context,
            if (isEnabled) R.color.white else R.color.disabled
        )
    ) ?: run {
        (this as? ImageView)?.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                if (isEnabled) R.drawable.ic_rectangle_enabled else R.drawable.ic_rectangle_disabled
            )
        )
    }
}