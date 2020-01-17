package com.example.ts.neteasytest.Fragments;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.example.ts.neteasytest.Adapter.AutoScrollViewPagerAdapter;
import com.example.ts.neteasytest.R;

import java.lang.reflect.Field;

/**
 * Created by ts on 20-1-16.
 */

public class AutoScrollViewPager extends ViewPager {
    private int mCount = PlayFragment.IMGS.length;//等于BaseFragment类中的IMG数组的长度
    private PagerAdapter mWrappedPagerAdapter;
    private PagerAdapter mWrapperPagerAdapter;
    private int intervalInMillis;
    private static final int MSG_AUTO_SCROLL = 0;
    private static final int DEFAULT_INTERNAL_IM_MILLIS = 2000;
    private OnPageChangeListener mOnPageChangeListener;
    private AutoScrollFactorScroller mAutoScrollFactorScroller;
    private HandlerForFragment mHandlerForFragment;
    private boolean mAutoScroll = false;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private float mLastMotionX;
    private float mLastMotionY;
    private InnerOnPageChangeListener mInnerOnPageChangeListener;
    private int mTouchStop;
    private OnPageClickListener mOnPageClickListener;
    public AutoScrollViewPager(Context context) {
        super(context);
        init();
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void selectItems(int index){
        for (int i = 0 ; i < mCount ; i++){
        ImageView imageView = (ImageView) PlayFragment.MROUNDLAYOUT.getChildAt(i);
        if (i  == index){
            imageView.setImageResource(R.drawable.dot_select);
        }
        else {
            imageView.setImageResource(R.drawable.dot_normal);
        }
        }
    }
    //选择图片
    public void init(){
        mInnerOnPageChangeListener = new InnerOnPageChangeListener();
        super.addOnPageChangeListener(mInnerOnPageChangeListener);
        mHandlerForFragment = new HandlerForFragment();
        //getScaledTouchSlop是一个距离，表示滑动的时候，
        // 手的移动要大于这个距离才开始移动控件。
        // 如果小于这个距离就不触发移动控件
        mTouchStop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandlerForFragment.removeMessages(MSG_AUTO_SCROLL);
    }

    public interface OnPageClickListener {
        void onPageClick(AutoScrollViewPager pager, int position);
    }
    public class InnerOnPageChangeListener implements OnPageChangeListener{
        private  OnPageChangeListener mOnPageChangeListener;
        private  int lastPageChangeListener = -1;

        public InnerOnPageChangeListener() {
        }
        public  InnerOnPageChangeListener(OnPageChangeListener onPageChangeListener){
            setOnPageChangeListener(onPageChangeListener);
        }

        public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
            this.mOnPageChangeListener = onPageChangeListener;
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mOnPageChangeListener != null && position > 0 && position < getCount()) {
                mOnPageChangeListener.onPageScrolled(position - 1, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            selectItems((position -1 ) % mCount);
            if (mOnPageChangeListener != null){
                final  int pos;
                //fix position
                // FIXME: 20-1-16
                if (position == 0){
                    pos = getCount() - 1 ;
                }
                else if (position == getCountOfWrapper() - 1){
                    pos = 0;
                }
                else {
                    pos = position - 1 ;
                }
                lastPageChangeListener = pos;
                AutoScrollViewPager.this.post(new Runnable() {
                    @Override
                    public void run() {
                        mOnPageChangeListener.onPageSelected(pos);
                    }
                });

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state   == SCROLL_STATE_IDLE && getChildCount() > 1){
                if (getCurrentItemOfWrapper() == 0){
                    // scroll to the last page
                    setCurrentItem(getCount() - 1 , false);
                }
                if (getCurrentItemOfWrapper() == getCurrentItemOfWrapper() - 1){
                    // scroll to the  SSCfirst page
                    setCurrentItem(0,false);
                }
                if (mOnPageChangeListener != null){
                    mOnPageChangeListener.onPageScrollStateChanged(state);
                }
            }

        }
    }
    private int getCurrentItemOfWrapper() {
        return super.getCurrentItem();
    }
    private int getCountOfWrapper() {
        if (mWrapperPagerAdapter != null) {
            return mWrapperPagerAdapter.getCount();
        }
        return 0;
    }
    private int getCount() {
        if (mWrappedPagerAdapter != null) {
            return mWrappedPagerAdapter.getCount();
        }
        return 0;
    }
    private void setScrollerIfNeeded() {
        if (mAutoScrollFactorScroller != null) {
            return;
        }
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);
            mAutoScrollFactorScroller = new AutoScrollFactorScroller(getContext(), (Interpolator) interpolatorField.get(null));
            scrollerField.set(this, mAutoScrollFactorScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class HandlerForFragment extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_AUTO_SCROLL:{
                    setCurrentItem(getCurrentItem() + 1);
                    Log.d("索引",getCurrentItem() + "");
                    selectItems(getCurrentItem());
                    sendEmptyMessageDelayed(MSG_AUTO_SCROLL,intervalInMillis);
                    break;
                }
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
    public void startAutoScroll() {
        startAutoScroll(intervalInMillis != 0 ? intervalInMillis : DEFAULT_INTERNAL_IM_MILLIS);
    }

    public void startAutoScroll(int intervalInMillis) {
        // Only post scroll message when necessary.
        if (getCount() > 1) {
            this.intervalInMillis = intervalInMillis;
            mAutoScroll = true;
            mHandlerForFragment.removeMessages(MSG_AUTO_SCROLL);
            mHandlerForFragment.sendEmptyMessageDelayed(MSG_AUTO_SCROLL, intervalInMillis);
        }
    }

    public void stopAutoScroll() {
        mAutoScroll = false;
        mHandlerForFragment.removeMessages(MSG_AUTO_SCROLL);
    }
    public void setInterval(int intervalInMillis) {
        this.intervalInMillis = intervalInMillis;
    }
    public void setScrollFactgor(double factor) {
        setScrollerIfNeeded();
        mAutoScrollFactorScroller.setFactor(factor);
    }
    public OnPageClickListener getmOnPageClickListener(){return mOnPageClickListener;}

    public void setmOnPageClickListener(OnPageClickListener mOnPageClickListener) {
        this.mOnPageClickListener = mOnPageClickListener;
    }

    @Override
    public void setCurrentItem(int item,boolean smoothScroll) {
        super.setCurrentItem(item + 1, smoothScroll);
    }

    @Override
    public int getCurrentItem() {
        int curr = super.getCurrentItem();
        if (mWrappedPagerAdapter != null && mWrappedPagerAdapter.getCount() > 1) {
            if (curr == 0) {
                curr = mWrappedPagerAdapter.getCount() - 1;
            } else if (curr == mWrapperPagerAdapter.getCount() - 1) {
                curr = 0;
            } else {
                curr = curr - 1;
            }
        }
        return curr;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item + 1);
    }


    @Override
    public PagerAdapter getAdapter() {
        return mWrappedPagerAdapter;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mWrappedPagerAdapter = adapter;
        mWrapperPagerAdapter = (mWrappedPagerAdapter == null) ? null : new AutoScrollViewPagerAdapter (adapter);
        super.setAdapter(mWrapperPagerAdapter);
        if (adapter != null&&adapter.getCount() !=0){
            post(new Runnable() {
                @Override
                public void run() {
                    setCurrentItem(0,false);
                }
            });
        }
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        this.mInnerOnPageChangeListener.setOnPageChangeListener(listener);
    }
//轮播
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:{
                if (getCurrentItemOfWrapper() + 1 == getCurrentItemOfWrapper()){
                    setCurrentItem(0, false);
                }
                else if (getCurrentItemOfWrapper() == 0){
                    setCurrentItem(getCount() - 1 , false) ;
                }
                mHandlerForFragment.removeMessages(MSG_AUTO_SCROLL);
                mInitialMotionX = ev.getX();
                mInitialMotionY = ev.getY();
                break;

            }
            case MotionEvent.ACTION_MOVE:
                mLastMotionX = ev.getX();
                mLastMotionY = ev.getY();
                if ((int) Math.abs(mLastMotionX - mInitialMotionX) > mTouchStop || (int) Math.abs(mLastMotionY - mInitialMotionY) > mTouchStop) {
                    mInitialMotionX = 0.0f;
                    mInitialMotionY = 0.0f;
                }
                break;
            case MotionEvent.ACTION_UP:{
                if (mAutoScroll){
                    startAutoScroll();
                }
                if (mAutoScrollFactorScroller != null){
                    final double lastFactor = mAutoScrollFactorScroller.getFactor();
                    mAutoScrollFactorScroller.setFactor(1);
                    post(new Runnable() {
                        @Override
                        public void run() {
                            mAutoScrollFactorScroller.setFactor(lastFactor);
                        }
                    });
                }

                mLastMotionX = ev.getX();
                mLastMotionY = ev.getY();
                if ((int) mInitialMotionX != 0 && (int) mInitialMotionY != 0) {
                    if ((int) Math.abs(mLastMotionX - mInitialMotionX) < mTouchStop
                            && (int) Math.abs(mLastMotionY - mInitialMotionY) < mTouchStop) {
                        mInitialMotionX = 0.0f;
                        mInitialMotionY = 0.0f;
                        mLastMotionX = 0.0f;
                        mLastMotionY = 0.0f;
                        if (mOnPageClickListener != null) {
                            mOnPageClickListener.onPageClick(this, getCurrentItem());
                        }
                    }
                }
                break;
            }
        }
        return super.onTouchEvent(ev);
    }
}
