package com.sunny.student.maodian;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.sunny.student.R;

public class MaoDianActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TabLayout mTabLayout;
    private LinearLayoutManager mLayoutManager;
    private SimpleAdapter mAdapter;

    private

    String[] strs = new String[]{"花生","瓜子","八宝粥","啤酒","饮料","矿泉水"};
    private int mToPosition;
    private boolean mShouldScroll;

    private boolean isFromTabScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mao_dian);
        mRecyclerView = findViewById(R.id.recyclerView);
        mTabLayout = findViewById(R.id.tableLayout);
        for (String str : strs) {
            mTabLayout.addTab(mTabLayout.newTab().setText(str));
        }
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleDecoration());
        mAdapter = new SimpleAdapter(strs);
        mRecyclerView.setAdapter(mAdapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                scrollRecyclerView(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 如果是Tab点击造成的滑动
                if (mShouldScroll) {
                    mShouldScroll = false;
                    int n = mToPosition - mLayoutManager.findFirstVisibleItemPosition();
                    if (n > 0 && n < mRecyclerView.getChildCount()) {
                        int top = mRecyclerView.getChildAt(n).getTop() - 100;
                        mRecyclerView.scrollBy(0, top);
                    }
                }

                if (isFromTabScroll) {
                    return;
                }
                //监听滑动距离，改变tab选中位置
                // 第一个可见View的 位置
                int realPosition = mLayoutManager.findFirstVisibleItemPosition();
                mTabLayout.setScrollPosition(realPosition, 0, true);

            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        isFromTabScroll = false;
                        break;
                }
//                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
//                    mShouldScroll = false;
//                    scrollRecyclerView(mToPosition);
//                }
            }
        });
    }


    private void convertPosition() {

    }
    private void scrollRecyclerView(int position) {
        isFromTabScroll = true;
        // 第一个可见位置
        int firstItem = mLayoutManager.findFirstVisibleItemPosition();
        // 最后一个可见位置
        int lastItem = mLayoutManager.findLastVisibleItemPosition();
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用scrollToPosition
            if (position != 0) {
                mToPosition = position;
                mShouldScroll = true;
            }
            mRecyclerView.scrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop() - 100;
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                mRecyclerView.scrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.scrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
    private static class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

        private String[] mList;
        public SimpleAdapter(String[] list) {
            mList = list;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maodian_recycler, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mTextView.setText(mList[position]);
        }

        @Override
        public int getItemCount() {
            return mList.length;
        }

        private static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.text);
            }
        }
    }

    private static class SimpleDecoration extends RecyclerView.ItemDecoration{

        Paint mPaint;

        public SimpleDecoration() {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
            mPaint.setColor(Color.BLUE);
        }
        @Override
        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDraw(c, parent, state);
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                // 拿到Item之后可以往里塞
                View view = parent.getChildAt(i);
                int left = 0;
                int top = view.getTop() - 100;
                int bottom = top + 100;
                int right = view.getRight();
                c.drawRect(left, top, right, bottom, mPaint);
            }

        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 100, 0, 0);
        }
    }
}