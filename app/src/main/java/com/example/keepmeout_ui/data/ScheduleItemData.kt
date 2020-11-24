package com.example.keepmeout_ui.data

import androidx.databinding.ObservableArrayList
import com.example.keepmeout_ui.BR
import com.example.keepmeout_ui.R
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

data class ScheduleItemData(val mLabel: String, val isEnabled: Boolean = true) {
    private val dayItems = ObservableArrayList<Any>()
    fun getDaysListItems(): ObservableArrayList<Any> {
        return dayItems
    }

    fun setDaysListItems(list: List<String>) {
        dayItems.clear()
        dayItems.addAll(list)
    }

    val accessoryDaysBinding: OnItemBindClass<Any> = OnItemBindClass<Any>()
        .map(String()::class.java) { itemBinding, _, _ ->
            itemBinding.clearExtras().set(BR.day, R.layout.layout_item_day_view)
                .bindExtra(BR.isEnabled, isEnabled)
        }
}