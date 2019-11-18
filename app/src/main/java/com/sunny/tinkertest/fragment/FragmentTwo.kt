package com.sunny.tinkertest.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.sunny.tinkertest.R

class FragmentTwo : Fragment() {

    companion object {
        fun newInstance(string : String = "1") : FragmentTwo {
            return FragmentTwo().apply {
                arguments = Bundle().apply {
                    putString("key", string)
                }
            }
        }
    }

    private lateinit var viewModel: FragmentTwoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_two_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentTwoViewModel::class.java)

        if ("2" == arguments?.get("key")) {
            val dialogFragment = DialogFragment()
            activity?.let {
                dialogFragment.showNow(childFragmentManager, "2")
            }

        }

    }

}
