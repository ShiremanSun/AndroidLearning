package com.sunny.student.banner.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunny.student.R;
import com.sunny.student.banner.BasePagerAdapter;

/**
 * created by sunshuo
 * on 2020-02-25
 */
public class BannerAdapter extends BasePagerAdapter<BannerAdapter.ViewHolder, String> {

    @Override
    public  BannerAdapter.ViewHolder onCreateViewHolder(ViewGroup container) {
        View  view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_banner, container, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mTextView = view.findViewById(R.id.title);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(BannerAdapter.ViewHolder viewHolder, String  data) {
        viewHolder.mTextView.setText(data);

    }


    public class ViewHolder extends BasePagerAdapter.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
