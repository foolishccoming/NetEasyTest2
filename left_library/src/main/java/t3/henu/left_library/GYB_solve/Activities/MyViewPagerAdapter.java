package t3.henu.left_library.GYB_solve.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;



public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public MyViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        this.fragments=fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

}
