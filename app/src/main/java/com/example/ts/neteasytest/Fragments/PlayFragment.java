package com.example.ts.neteasytest.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.view.PagerAdapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ts.neteasytest.Adapter.SongListViewAdapter;
import com.example.ts.neteasytest.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ts on 20-1-14.
 */

public class PlayFragment  extends Fragment{
    public static String[] IMGS = {
            "http://p1.music.126.net/MIp47rKLmcltwKek8sjiPQ==/109951164625323769.jpg?imageView&quality=89",
            "http://p1.music.126.net/l0zLY7X2lVcgANj4XVY_Yw==/109951164625326299.jpg?imageView&quality=89",
            "http://p1.music.126.net/BpCiCJgst2Fk0nX0EDY6rA==/109951164626205878.jpg?imageView&quality=89",
            "http://p1.music.126.net/6urcHOsBaIUGHkUulXM5fw==/109951164625597138.jpg?imageView&quality=89",
            "http://p1.music.126.net/171SL-dmuByyWMlaCCX2qQ==/109951164625579317.jpg?imageView&quality=89",
            "http://p1.music.126.net/wIsOIA9L8A6aotN8xfxcYg==/109951164627166440.jpg?imageView&quality=89"
    };
    public static LinearLayout MROUNDLAYOUT;
    //小圆点布局
    private String[] title = {"音乐 >","视频 >","电台 >","测试 >" };
    private int[] image = {R.mipmap.p1, R.mipmap.p2, R.mipmap.p3
            , R.mipmap.p4, R.mipmap.p5, R.mipmap.p6
            , R.mipmap.p7, R.mipmap.p8, R.mipmap.p9
            , R.mipmap.p10, R.mipmap.p11, R.mipmap.p12};
    private String[] describe = {"第一首","第二首","第三首","第四首","第五首","第六首","第七首","第八首","第九首","第十首","第十一首","第十二首"};
    private List<SelectItems> selectItemsesList = new ArrayList<>();
    private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
    private Toast mToast;
    private View mView;
    public PlayFragment(){}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_blank,container,false);
        MROUNDLAYOUT = (LinearLayout) mView.findViewById(R.id.dot_layout);
        selectItemsesList.clear();
        addPageImage();
        initSelect();
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.select_type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        SongListViewAdapter adapter = new SongListViewAdapter(selectItemsesList);
        recyclerView.setAdapter(adapter);
        return mView;
    }
    private void initSelect(){
        for (int i = 1;i<=title.length;i++){
            SelectItems selectItems = new SelectItems(title[i - 1], image[3 * i - 3], image[3 * i - 2], image[3 * i - 1], describe[3 * i - 3], describe[3 * i - 2], describe[3 * i - 1]);
            selectItemsesList.add(selectItems);
        }

    }
    //圆点切换
    public void addPageImage(){
        for(int i = 0; i < IMGS.length; i++ ){
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5,5,5,5);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(R.drawable.dot_normal);
            MROUNDLAYOUT.addView(imageView);
        }
        ImageView imageViewS = (ImageView) PlayFragment.MROUNDLAYOUT.getChildAt(0);
        imageViewS.setImageResource(R.drawable.dot_select);
    }

    public void onViewCreated(final View view , Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        AutoScrollViewPager autoScrollViewPager = (AutoScrollViewPager) mView.findViewById(R.id.scroll_pager);

        autoScrollViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return IMGS.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
                imageLoader.displayImage(IMGS[position], imageView, options);
                container.addView(imageView);
                return imageView;

            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

        });
        autoScrollViewPager.setScrollFactgor(5);
        autoScrollViewPager.setOffscreenPageLimit(4);
        autoScrollViewPager.startAutoScroll(4000);
        autoScrollViewPager.setmOnPageClickListener(new AutoScrollViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(AutoScrollViewPager pager, int position) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = mToast.makeText(getActivity(), "You clicked page: " + (position + 1),
                        mToast.LENGTH_SHORT);
                mToast.show();
            }
        });

    }
}
