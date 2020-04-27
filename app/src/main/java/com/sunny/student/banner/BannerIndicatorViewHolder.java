package com.sunny.student.banner;

import android.view.View;

/**
 * created by sunshuo
 * on 2020-02-26
 */
public abstract class BannerIndicatorViewHolder {
    public BannerIndicatorViewHolder(View view) {
        mView = view;
    }
    public View getView() {
        return mView;
    }
    private View mView;
    public abstract void select();
    public abstract void unSelect();
}
