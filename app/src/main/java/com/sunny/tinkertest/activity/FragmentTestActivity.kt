package com.sunny.tinkertest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.livedatabus.LiveDataBus
import com.sunny.tinkertest.R
import com.sunny.tinkertest.fragment.FragmentOne
import com.sunny.tinkertest.fragment.FragmentTwo

class FragmentTestActivity : AppCompatActivity() {

    val transaction = supportFragmentManager.beginTransaction()
    val dialogFragmentOne = DialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)
        val fragment1 = FragmentOne.newInstance("12", "12")
        val fragment2 = FragmentTwo.newInstance()
        transaction.add(R.id.container, fragment1,"1")
        transaction.commit()

        LiveDataBus.getChannel<String>("1").observe(this, Observer{
            when (it) {
                "1" -> {
                    transaction.replace(R.id.container, fragment2, "2")
                    transaction.commit()
                }
                "2" -> {
                    transaction.replace(R.id.container, fragment2, "2")
                    transaction.commitNow()
                }
                "3" -> {
                    dialogFragmentOne.show(supportFragmentManager, "3")
                    if (!dialogFragmentOne.isAdded ||
                            supportFragmentManager.findFragmentByTag("3") == null) {
                        dialogFragmentOne.show(supportFragmentManager, "3")
                    }
                }
                "4" -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container, fragment2, "2")
                    transaction.addToBackStack(null)
                    transaction.commitNow()
                }
                "5" -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    Handler().postDelayed({
                        transaction.replace(R.id.container, fragment2, "2")
                        transaction.addToBackStack("2")
                        transaction.commitAllowingStateLoss()
                    }, 3000)
                }
                "6" -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    val fragmentTwo = FragmentTwo.newInstance(string = "2")
                    transaction.replace(R.id.container, fragmentTwo, "2")
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }


        })

    }

    override fun onDestroy() {
        super.onDestroy()
        LiveDataBus.unRegister("1")
    }
}
