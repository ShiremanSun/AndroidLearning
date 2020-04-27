package com.sunny.student.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.livedatabus.LiveDataBus
import com.sunny.student.R
import kotlinx.android.synthetic.main.fragment_fragment_one.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentOne : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_fragment_one, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        replace.setOnClickListener {
            LiveDataBus.getChannel<String>("1").value = "1"
        }


        button3.setOnClickListener {
            LiveDataBus.getChannel<String>("1").value = "2"
        }

        button4.setOnClickListener {
            LiveDataBus.getChannel<String>("1").value = "3"
        }

        button5.setOnClickListener {
            LiveDataBus.getChannel<String>("1").value = "4"
        }
        button6.setOnClickListener {
            LiveDataBus.getChannel<String>("1").value = "5"
        }

        button7.setOnClickListener {
            LiveDataBus.getChannel<String>("1").value = "6"
        }


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FragmentOne().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
