package com.sunny.student.banner;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * created by sunshuo
 * on 2020-02-25
 */
public abstract class BasePagerAdapter<VH extends BasePagerAdapter.ViewHolder, E> extends PagerAdapter {
    private LinkedList<VH> mCacheVH = new LinkedList<>();

    private List<E> mData;

    public void setData(List<E> data) {
        mData = new ArrayList<>();
        if (data.size() > 1) {
            mData.add(data.get(data.size() - 1));
            mData.addAll(data);
            mData.add(data.get(0));
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public final Object instantiateItem(@NonNull ViewGroup container, int position) {
        VH view = null;

        if (mCacheVH.size() > 0) {
           view = mCacheVH.getFirst();
           mCacheVH.removeFirst();
        }
        if (view == null) {
            view = onCreateViewHolder(container);
        }

        onBindViewHolder(view, mData.get(position));
        view.getItemView().setTag(view);
        container.addView(view.getItemView());
        return view;
    }


    @Override
    public final void destroyItem(ViewGroup container, int position,  Object object) {
        VH view = (VH) object;
        container.removeView(view.getItemView());
        mCacheVH.addLast(view);
    }

    @Override
    public final boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((VH)object).getItemView();
    }


    public abstract VH onCreateViewHolder(ViewGroup viewGroup);
    public abstract void onBindViewHolder(VH viewHolder, E data);


    @Override
    public int getCount() {
        return mData.size();
    }

    public abstract class ViewHolder{
        protected View mView;
        public ViewHolder(View itemView) {
            mView = itemView;
        }
        public View getItemView() {
            return mView;
        }
    }
}