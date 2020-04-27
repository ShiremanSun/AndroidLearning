package com.sunny.student.viewmodel;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * created by sunshuo
 * on 2020-02-04
 * 生命周期比Activity略长
 */
public class ViewModelImp extends ViewModel {
    private int count = 0;
    public void add() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //mLiveData.setValue(++count);
            }
        }, 5000);

        mLiveData.setValue(++count);
    }
    public void add(int count) {
        mCommonLiveData.setValue(count);
    }
    public MutableLiveData<Integer> mCommonLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> mLiveData = new MutableLiveData<>();
}
