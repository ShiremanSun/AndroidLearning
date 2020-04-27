package com.sunny.student.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.ViewPager;

import com.sunny.student.R;

import java.util.ArrayList;
import java.util.List;

/**
 * created by sunshuo
 * on 2020-02-25
 * 无限循环广告轮播图
 * 实现方式是在左边添加一个最后的数据，在右边添加第一个数据
 * 方向是向左滑动
 * 第0个view和最后一个只是为了占位
 */
public class Banner extends ConstraintLayout implements ViewPager.OnPageChangeListener, LifecycleEventObserver {

    private BannerViewPager mViewPager;


    private BasePagerAdapter mPagerAdapter;


    public void setIndicatorAdapter(BannerIndicatorAdapter indicatorAdapter) {
        mIndicatorAdapter = indicatorAdapter;
    }

    public BannerIndicatorAdapter mIndicatorAdapter;

    private boolean isAutoPlay;
    private int count; //数据源的数量

    private int delayTime;
    private Handler mHandler;
    private int mLastPosition;

    public void setMarginBottom(int marginBottom) {
        mMarginBottom = marginBottom;
    }

    private int mMarginBottom;



    private boolean isIndicatorVisible;
    private int currentItem = 1;
    private Runnable mTask; //轮播的任务
    boolean flag;
    private List<BannerIndicatorViewHolder> mIndicators;
    public void setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
    }
    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attributeSet) {
        isAutoPlay = true;
        count = 0;
        delayTime = 3000;
        isIndicatorVisible = false;
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.Banner);
        int mode = array.getInteger(R.styleable.Banner_mode, 0);
        array.recycle();
        View view;
        if (mode == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.view_normal_banner, this, true);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.view_clip_banner, this, true);
        }

        mViewPager = view.findViewById(R.id.banner_viewPager);
        mViewPager.setOffscreenPageLimit(4);
        if (mode != 0) {
            mViewPager.setPageTransformer(false, new ScaleYTransformer());
        }

        mHandler = new Handler(Looper.getMainLooper());
        mTask = new Runnable() {
            @Override
            public void run() {
                if (mPagerAdapter != null && count > 1 && isAutoPlay) {
                    //下一页的位置
                    currentItem = mViewPager.getCurrentItem() + 1;
                    mViewPager.setCurrentItem(currentItem, true);
                    //发送消息，相当于循环任务
                    mHandler.postDelayed(this, delayTime);
                }
            }
        };

        flag = false;

    }

    public void setIndicatorVisible(boolean indicatorVisible) {
        isIndicatorVisible = indicatorVisible;
    }


    public void build() {
        mViewPager.addOnPageChangeListener(this);
        if (isIndicatorVisible) {
            if (mIndicatorAdapter == null) {
                throw new IllegalStateException("when set isIndicatorVisible mIndicatorAdapter must not be null!");
            }
            LinearLayout indicatorContainer = new LinearLayout(getContext());
            indicatorContainer.setOrientation(LinearLayout.HORIZONTAL);
            ConstraintLayout.LayoutParams containerParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            containerParams.startToStart = getId();
            containerParams.endToEnd = getId();
            containerParams.bottomToBottom = getId();
            containerParams.bottomMargin = mMarginBottom;
            addView(indicatorContainer, containerParams);
            mIndicators = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                BannerIndicatorViewHolder holder = mIndicatorAdapter.onCreateViewHolder(indicatorContainer);
                View view = holder.getView();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.width = 0;
                layoutParams.weight = 1;
                mIndicators.add(holder);
                indicatorContainer.addView(view);
            }
            mIndicators.get(0).select();
        }
    }


    public void setAdapter(BasePagerAdapter pagerAdapter) {
        mPagerAdapter = pagerAdapter;
        if (pagerAdapter.getCount() > 2) {
            count = pagerAdapter.getCount() - 2;
        } else {
            count = pagerAdapter.getCount();
        }
        mViewPager.setAdapter(mPagerAdapter);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //滑动结束
        if (state == ViewPager.SCROLL_STATE_IDLE) {//如果滑动到第一个位置，因为第一个位置是最后一个数据，所以滑动到最后一个数据
            currentItem = mViewPager.getCurrentItem();
            if (isIndicatorVisible) {
                mIndicators.get(mLastPosition).unSelect();
            }
            if (currentItem == 0) {
                mViewPager.setCurrentItem(count, false);
                if (isIndicatorVisible) {
                    mIndicators.get(count - 1).select();
                    mLastPosition = count - 1;
                }
            }
            //如果滑动到最后一个位置，是第一个数据，所以滑动到第一个数据
            else if (currentItem == count + 1) {
                mViewPager.setCurrentItem(1, false);
                if (isIndicatorVisible) {
                    mIndicators.get(0).select();
                    mLastPosition = 0;
                }
            } else {
                if (isIndicatorVisible) {
                    mIndicators.get(currentItem - 1).select();
                    mLastPosition = currentItem - 1;
                }
            }
        }
    }

    public void start() {
        //如果展示指示器
        mViewPager.setCurrentItem(currentItem, false);
        //先移除再添加
        mHandler.removeCallbacks(mTask);
        mHandler.postDelayed(mTask, delayTime);
    }


    public void pause() {
        mHandler.removeCallbacks(mTask);
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        switch (event) {
            case ON_PAUSE:
                pause();
                break;
        }
    }
}
