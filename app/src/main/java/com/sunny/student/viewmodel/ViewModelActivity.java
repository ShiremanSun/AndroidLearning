package com.sunny.student.viewmodel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sunny.student.R;

public class ViewModelActivity extends AppCompatActivity {

    int count = 0;

    public MutableLiveData<Integer> mOnlyLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model);
        //实例化无参构造方法的ViewModel
        final ViewModelImp viewModel = ViewModelProviders.of(this).get(ViewModelImp.class);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        final TextView textView = findViewById(R.id.textView);
        final TextView common = findViewById(R.id.common);
        final TextView commonLiveData = findViewById(R.id.textView3);
        final TextView onlyLiveData = findViewById(R.id.textView4);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.add();

                common.setText(String.valueOf(++count));
                viewModel.add(count);
                mOnlyLiveData.setValue(count);
            }
        });
        viewModel.mLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("LiveData", integer.toString());
                textView.setText(String.valueOf(integer));
            }
        });
        viewModel.mCommonLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                commonLiveData.setText(String.valueOf(integer));
            }
        });

        mOnlyLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                onlyLiveData.setText(String.valueOf(integer));
            }
        });
    }
}
