package t3.henu.left_library.GYB_solve.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import t3.henu.left_library.GYB_solve.Activities.Fragments.*;
import t3.henu.left_library.*;
import t3.henu.left_library.GYB_solve.All_View;
import t3.henu.left_library.GYB_solve.Collect;
import t3.henu.left_library.GYB_solve.MainActivity;
import t3.henu.left_library.GYB_solve.RobotFragment.ChatFragment;
import t3.henu.left_library.YYN_solve.AlbumFragment;
import t3.henu.left_library.YYN_solve.SingerFragment;



public class MyMusic extends MainActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private All_View all_view=null;
    private List<Fragment> fragments=new ArrayList<Fragment>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyb_activity_bendiyinyue);
        initView();
        all_view=new All_View(MyMusic.t_singer,MyMusic.t_songname,MyMusic.imageView
        ,MyMusic.btn_play);
        Collect.addView(all_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Collect.removeView(all_view);
    }

    private void initView() {
        initViewPager();
       initTabLayout();

    }

    private void initTabLayout() {
        tabLayout= (TabLayout) findViewById(R.id.id_mymusic_tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViewPager() {
        viewPager= (ViewPager) findViewById(R.id.id_mymusic_viewpager);
        if(fragments.size()<=0){
            fragments.add(new SongRecyclerview());
            fragments.add(new SingerFragment());
            fragments.add(new AlbumFragment());
            fragments.add(new FragmentFile());
            viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragments));
        }


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void finish(View v){
        MyMusic.this.finish();
    }
}
