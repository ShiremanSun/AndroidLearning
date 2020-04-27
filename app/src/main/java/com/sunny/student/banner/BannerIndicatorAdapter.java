package com.sunny.student.banner;

import android.view.ViewGroup;

/**
 * created by sunshuo
 * on 2020-02-26
 */
public interface BannerIndicatorAdapter<T extends BannerIndicatorViewHolder> {
    T onCreateViewHolder(ViewGroup parent);
}
