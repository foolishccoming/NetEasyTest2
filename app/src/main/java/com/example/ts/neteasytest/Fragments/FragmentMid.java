package com.example.ts.neteasytest.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ts.neteasytest.Adapter.NetEasyViewPagerAdapter;
import com.example.ts.neteasytest.R;

import java.util.ArrayList;
import java.util.List;

import t3.henu.left_library.CHY_solve.FragmentZhubo;
import t3.henu.left_library.FZ_solve.fz_fragment;
import t3.henu.left_library.YHQ_solve.BillboardFragment;

/**
 * Created by ts on 20-1-14.
 */

public class FragmentMid extends Fragment {
    private View mRootView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_main_appmain_tablayout_mid,container,false);
        initViewPager();
        initTabLayout();
        return mRootView;
    }
    private void initTabLayout(){
        mTabLayout = (TabLayout) mRootView.findViewById(R.id.id_appmain_toolbar_tabLayout_mid_tablayout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(mTabLayout.getSelectedTabPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void initViewPager(){
        mViewPager = (ViewPager) mRootView.findViewById(R.id.id_appmain_toolbar_tabLayout_mid_viewPager);
        if (mFragmentList.size()<=0){
            mFragmentList.add(new PlayFragment());
            mFragmentList.add(new fz_fragment());
            mFragmentList.add(new AnchorFragment());
            mFragmentList.add(new BillboardFragment());
        }
        NetEasyViewPagerAdapter netEasyViewPagerAdapter = new NetEasyViewPagerAdapter(getActivity().getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(netEasyViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                mTabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
