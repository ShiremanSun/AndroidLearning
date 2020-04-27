package com.sunny.student.banner;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * created by sunshuo
 * on 2020-02-25
 */
public class ScaleYTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position == -1F) {
            page.setScaleY(0.9F);
        } else if (position == 0F){
            page.setScaleY(1F);
        } else {
            page.setScaleY(0.9F);
        }
    }
}
