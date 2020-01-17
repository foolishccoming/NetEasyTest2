package com.example.ts.neteasytest.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ts on 20-1-14.
 */

public class NetEasyViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    public NetEasyViewPagerAdapter(FragmentManager fragmentManager,List<Fragment> mFragmentList) {
        super(fragmentManager);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
