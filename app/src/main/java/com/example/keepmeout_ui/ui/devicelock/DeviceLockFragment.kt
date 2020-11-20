package com.example.keepmeout_ui.ui.devicelock

import android.os.Bundle
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
import com.example.keepmeout_ui.databinding.FragmentMainBinding
import github.hellocsl.cursorwheel.CursorWheelLayout

/**
 * A placeholder fragment containing a simple view.
 */
class DeviceLockFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private lateinit var viewBinding: FragmentMainBinding
    private val hoursItemData: MutableList<String> by lazy {
        val data = mutableListOf<String>()
        for (i in 0..11) {
            if (i < 10) data.add("0$i")
            else data.add(i.toString())
        }
        data.addAll(data)
        data
    }

    private val simpleTextAdapter: SimpleTextAdapter? by lazy {
        context?.let {
            SimpleTextAdapter(it, hoursItemData)
        }
    }

    private val minutesItemData: MutableList<String> by lazy {
        val data = mutableListOf<String>()
        for (i in 0..59) {
            if ((i % 5 == 0 || i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 59)) {
                if (i < 10) data.add("0$i")
                else data.add(i.toString())
            }
        }
        data
    }

    private val simpleTextAdapter1: SimpleTextAdapter? by lazy {
        context?.let {
            SimpleTextAdapter(it, minutesItemData)
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
                Toast.makeText(
                    context, "Hour selected :${hoursItemData[pos]}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewBinding.circularMinutesMenu.setAdapter(simpleTextAdapter1)
        viewBinding.circularMinutesMenu.setOnMenuSelectedListener(object :
            CursorWheelLayout.OnMenuSelectedListener {
            override fun onItemSelected(parent: CursorWheelLayout, view: View?, pos: Int) {
                Toast.makeText(
                    context, "Minute selected :${minutesItemData[pos]}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}