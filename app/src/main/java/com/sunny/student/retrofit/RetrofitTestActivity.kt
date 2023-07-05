package com.sunny.student.retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sunny.student.databinding.ActivityRetrofitTestBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class RetrofitTestActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityRetrofitTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRetrofitTestBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        lifecycleScope.launch(Dispatchers.Unconfined){
            try {
                val result = RetrofitProvider.getInstance().create(BaiduApi::class.java).getBaiduHtmlSuspend()
                mBinding.content.setText(result.body())
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }

        /*lifecycleScope.launchWhenCreated {
            Log.e("launchWhenCreated", "launchWhenCreated")
        }

        lifecycleScope.launchWhenStarted {

        }

        lifecycleScope.launchWhenResumed {

        }


        lifecycleScope.launch {

        }


        val call = RetrofitProvider.getInstance().create(BaiduApi::class.java).getBaiduHtmlNormal();

        call.enqueue(object : retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                mBinding.content.setText(response.body())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }

        })*/

        /*thread {
            try {
                val response = call.execute()
                runOnUiThread {
                    mBinding.content.setText(response.body())
                }
            } catch (e :Exception) {
                // TODO Error
            }
        }*/
        var startTime = System.currentTimeMillis()
        for (i in 0 until 1000) {
            val job = GlobalScope.launch(Dispatchers.Default) {
                Thread.sleep(100)
                Log.e("CoroutineTime", (System.currentTimeMillis() - startTime).toString());
            }

        }

        val startTime1 = System.currentTimeMillis()
        val executor = Executors.newFixedThreadPool(8)
        for (i in 0 until 1000) {
            executor.execute {
                Thread.sleep(100)
                Log.e("ExecutorTime", (System.currentTimeMillis() - startTime1).toString());
            }
        }




        /*request(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                request(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                    }

                }, response.message())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }

        }, "1")*/


        //chuanxing()

        //combine()

        //testTopAsync()
    }

    private fun chuanxing() {
       val job = GlobalScope.launch(Dispatchers.Main) {
            var result1 : String = ""
            try {
                result1 = request("1")
            } catch (e : Exception) {
                e.printStackTrace()
            }
            if (result1.isNotEmpty()) {
                try {
                    request(result1)
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    fun combine() {
        var countDownLatch = 2
        request(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                countDownLatch--
                if (countDownLatch == 0) {
                    // do somthing
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                countDownLatch--
                if (countDownLatch == 0) {
                    // do somthing
                }
            }

        }, "1")

        request(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                countDownLatch--
                if (countDownLatch == 0) {
                    // do somthing
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                countDownLatch--
                if (countDownLatch == 0) {
                    // do somthing
                }
            }

        }, "2")
        GlobalScope.launch {
            try {
                val result1 = asyncRequest("1")
                val result2 = asyncRequest("2")
                val combinedResult = result1.await() + result2.await()
            } catch (e : Exception) {
                e.printStackTrace()
            }

        }

    }

    private suspend fun getData() : String{
        return withContext(Dispatchers.IO) {
            Thread.sleep(5000)
            "test"
        }
    }



    private fun request(callback: Callback<String>, params : String) {

    }

    private suspend fun request(params: String) : String{
        val reseult = withContext(Dispatchers.IO) {
            "test"
        }
        return reseult
    }



    private fun testTopAsync() {
        runBlocking {

        }
        try {
            GlobalScope.async(Dispatchers.Main) {
                request("1")
                throw NullPointerException()

            }
        } catch (e : Exception) {
            e.printStackTrace()
        }

    }
    private fun asyncRequest(params: String): Deferred<String> {
        return GlobalScope.async(Dispatchers.Main) {
            request("1")
        }
    }


    fun test(n : Int) : Int{
        if (n == 1) return 1
        return n * test(n - 1)
    }

}