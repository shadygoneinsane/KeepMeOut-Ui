package com.example.keepmeout_ui.ui.devicelock

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.keepmeout_ui.BR
import com.example.keepmeout_ui.R
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
        items.clear()
        items.add("abc")
    }

    val accessoryBinding: OnItemBindClass<Any> = OnItemBindClass<Any>()
        .map(String::class.java) { itemBinding, _, _ ->
            itemBinding.clearExtras().set(BR.toggle, R.layout.layout_item_schedule_lock)
        }
}