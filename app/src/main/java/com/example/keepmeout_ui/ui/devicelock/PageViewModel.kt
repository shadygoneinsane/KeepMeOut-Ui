package com.example.keepmeout_ui.ui.devicelock

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.keepmeout_ui.BR
import com.example.keepmeout_ui.R
import com.example.keepmeout_ui.data.ScheduleItemData
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    private val items = ObservableArrayList<Any>()
    fun getListItems(): ObservableArrayList<Any> {
        return items
    }

    fun setListItems() {
        if (items.size == 1) {
            items.clear()
            items.add(ScheduleItemData("study").apply {
                setDaysListItems(listOf("W", "T", "F"))
            })
            items.add(ScheduleItemData("work", false).apply {
                setDaysListItems(listOf("M", "T", "W", "T", "F"))
            })
            items.add(ScheduleItemData("meditation").apply {
                setDaysListItems(listOf("Everyday"))
            })
        } else
            inflateEmpty()
    }

    fun inflateEmpty() {
        items.clear()
        items.add("empty")
    }

    val accessoryBinding: OnItemBindClass<Any> = OnItemBindClass<Any>()
        .map(ScheduleItemData::class.java) { itemBinding, _, _ ->
            itemBinding.clearExtras().set(BR.item, R.layout.layout_item_schedule_lock)
        }
        .map(String()::class.java) { itemBinding, _, _ ->
            itemBinding.clearExtras().set(BR.data, R.layout.layout_item_empty_view)
        }
}