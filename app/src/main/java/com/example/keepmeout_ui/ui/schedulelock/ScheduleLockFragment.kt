package com.example.keepmeout_ui.ui.schedulelock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.keepmeout_ui.R
import com.example.keepmeout_ui.databinding.FragmentScheduleBinding
import com.example.keepmeout_ui.ui.devicelock.PageViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class ScheduleLockFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private lateinit var viewBinding: FragmentScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_schedule, container, false)
        viewBinding = DataBindingUtil.bind(root)!!
        viewBinding.viewModel = pageViewModel
        viewBinding.lifecycleOwner = this
        pageViewModel.inflateEmpty()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}