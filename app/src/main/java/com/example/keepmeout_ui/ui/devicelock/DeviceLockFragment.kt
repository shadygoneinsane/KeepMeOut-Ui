package com.example.keepmeout_ui.ui.devicelock

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.keepmeout_ui.R
import com.example.keepmeout_ui.adapter.SimpleTextAdapter
import com.example.keepmeout_ui.data.MenuItemData
import com.example.keepmeout_ui.databinding.FragmentMainBinding
import github.hellocsl.cursorwheel.CursorWheelLayout
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class DeviceLockFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private lateinit var viewBinding: FragmentMainBinding

    private val simpleTextAdapter: SimpleTextAdapter? by lazy {
        val res = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "OFF")
        val menuItemData: MutableList<MenuItemData> = ArrayList()
        for (i in res.indices) {
            menuItemData.add(MenuItemData(res[i]))
        }

        context?.let {
            SimpleTextAdapter(it, menuItemData, Gravity.TOP or Gravity.CENTER_HORIZONTAL)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        viewBinding = DataBindingUtil.bind(root)!!
        viewBinding.lifecycleOwner = this
        pageViewModel.text.observe(this, Observer<String> {
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.circularHourMenu.setAdapter(simpleTextAdapter)
        viewBinding.circularHourMenu.setOnMenuSelectedListener(object :
            CursorWheelLayout.OnMenuSelectedListener {
            override fun onItemSelected(parent: CursorWheelLayout, view: View?, pos: Int) {
                Toast.makeText(context, "Hour Menu selected position:$pos", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}