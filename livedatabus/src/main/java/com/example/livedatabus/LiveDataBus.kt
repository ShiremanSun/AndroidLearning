package com.example.livedatabus

import androidx.lifecycle.MutableLiveData


/**
 * 基于LiveData的消息总线
 * LiveData天生的观察者模式，订阅者只需要去订阅自己的LiveData
 */
object LiveDataBus {

    //保存所有的LiveData
    private val bus = HashMap<String, MutableLiveData<*>>()

    fun <T> getChannel(channelId : String) : MutableLiveData<T>{
        if (!bus.containsKey(channelId)) {
            bus[channelId] = MutableLiveData<T>()
        }
        return bus[channelId] as MutableLiveData<T>
    }


    fun unRegister(channelId: String) {
        bus.remove(channelId)
    }
}