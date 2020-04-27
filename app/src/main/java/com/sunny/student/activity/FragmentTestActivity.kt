package com.sunny.student.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.Observer
import com.example.livedatabus.LiveDataBus
import com.sunny.student.R
import com.sunny.student.fragment.FragmentOne
import com.sunny.student.fragment.FragmentTwo
import com.sunny.student.fragment.MyDialogFragment

class FragmentTestActivity : AppCompatActivity() {

    val transaction = supportFragmentManager.beginTransaction()
    val dialogFragmentOne = MyDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)



        var fragment1 = supportFragmentManager.findFragmentByTag("1")
        if (fragment1 == null){
            fragment1 = FragmentOne.newInstance("1", "2")
            transaction.add(R.id.container,fragment1, "1").commitAllowingStateLoss()
        }

        var fragment2 : FragmentTwo? = supportFragmentManager.findFragmentByTag("2") as FragmentTwo?
        if (fragment2 == null) {
            fragment2 = FragmentTwo.newInstance()
        }

        LiveDataBus.getChannel<String>("1").observe(this, Observer{
            Log.d("restore1", it)
            when (it) {
                "1" -> {
                    //全局的transaction
                     val tran =supportFragmentManager.beginTransaction()
                    tran.replace(R.id.container, fragment2, "2")
                    tran.commit()
                }
                "2" -> {
                    //两次 commit相隔时间太短
                    dialogFragmentOne.showNow(supportFragmentManager, "3")
                    if (!dialogFragmentOne.isAdded ||
                            supportFragmentManager.findFragmentByTag("3") == null) {
                        dialogFragmentOne.show(supportFragmentManager, "3")
                    }
                }
                "3" -> {
                    //错误的添加到返回栈
                    val transaction = supportFragmentManager.beginTransaction()
                    fragment2.let { it1 -> transaction.replace(R.id.container, it1, "2") }
                    transaction.addToBackStack(null)
                    transaction.commitNow()
                }
                "4" -> {
                    //在onSavedState后调用commit
                    val transaction = supportFragmentManager.beginTransaction()
                    Handler().postDelayed({
                        fragment2.let { it1 -> transaction.replace(R.id.container, it1, "2") }
                        transaction.addToBackStack("2")
                        transaction.commitAllowingStateLoss()
                    }, 3000)
                }
                "5" -> {
                    //在同一个FragmentManager的一个事务里面，再使用该FragmentManager进行提交
                    val transaction = supportFragmentManager.beginTransaction()
                    val fragmentTwo1 = FragmentTwo.newInstance("2")
                    transaction.replace(R.id.container, fragmentTwo1, "2")
                    transaction.addToBackStack(null)
                    transaction.commit()
//                    val fragmentTwo= FragmentTwo.newInstance()
//                    val transaction1 = supportFragmentManager.beginTransaction()
//                    transaction1.replace(R.id.container, fragmentTwo, "2")
//                    transaction1.commitNow()
                }

//                "6" -> {
//                    val transaction = supportFragmentManager.beginTransaction()
//                    if (supportFragmentManager.findFragmentByTag("2") == null) {
//                        val fragmentTwo = FragmentTwo.newInstance(string = "3")
//                        transaction.replace(R.id.container, fragmentTwo, "2")
//                        transaction.addToBackStack(null)
//                        transaction.commitAllowingStateLoss()
//                    }
//
//                }
            }


        })

    }

    override fun onDestroy() {
        super.onDestroy()
        LiveDataBus.unRegister("1")
    }
}
