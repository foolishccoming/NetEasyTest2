package com.example.ts.neteasytest.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ts on 20-1-16.
 */

public class AutoScrollViewPagerAdapter extends PagerAdapter {
    private PagerAdapter mPagerAdapter;
    public AutoScrollViewPagerAdapter(PagerAdapter pagerAdapter){
        mPagerAdapter = pagerAdapter;
    }
    @Override
    public int getCount() {
        if ( mPagerAdapter == null){
        return 0;}
        else if (mPagerAdapter.getCount() > 1){
            return mPagerAdapter.getCount()+2;
        }
        else {
            return mPagerAdapter.getCount();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return mPagerAdapter.isViewFromObject(view,0);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (position == 0) {
            return mPagerAdapter.instantiateItem(container, mPagerAdapter.getCount() - 1);
        } else if (position == mPagerAdapter.getCount() + 1) {
            return mPagerAdapter.instantiateItem(container, 0);
        } else {
            return mPagerAdapter.instantiateItem(container, position - 1);
        }
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mPagerAdapter.destroyItem(container, position, object);
    }

}
