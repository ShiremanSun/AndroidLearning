package com.sunny.student.login

/**
 * created by sunshuo
 * on 2020-02-28
 */
interface ILoginListener {
    fun onSuccess(code : String)
    fun onSuccess(map: HashMap<String, String>)
    fun onError(message : String)
    fun onCancel(message : String)
}