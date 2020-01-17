package com.example.ts.neteasytest;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ts.neteasytest.Adapter.NetEasyViewPagerAdapter;
import com.example.ts.neteasytest.Adapter.RecyclerviewAdapter;
import com.example.ts.neteasytest.Fragments.AllBaseFragment;
import com.example.ts.neteasytest.Fragments.FragmentMid;
import com.example.ts.neteasytest.LoginThings.LoginActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import t3.henu.left_library.GYB_solve.Activities.NetWork.SearchActivity;
import t3.henu.left_library.GYB_solve.Fragments.LeftRecyclerView;
import t3.henu.left_library.GYB_solve.RobotFragment.ChatFragment;
import t3.henu.left_library.GYB_solve.Services.PlayService;
import t3.henu.left_library.XPD_solve.MainActivity_XPD;


public class MainActivity extends AppCompatActivity {
    public static TextView MSONGTEXTVIEW,MPLAYERTEXTVIEW;
    public static ImageButton MBTNPLAY;
    public static ImageView MUSERIMAGE;
    public static RoundedImageView mRoundedImageView;
    public static boolean MISLOG = false;
    public static Button mBtnLog;
    final private static int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    final private static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 122;
    final private static int READ_SMS_REQUES_CODE = 122;
    final private static int READ_EXTERNAL_STORAGE_REQUEST_CODE = 123;
    Fragment mFrafmentLeft = null , mChatFragment = null;
    List<RecyclerViewData> mRecyclerViewDatasList = new ArrayList<RecyclerViewData>();
    Intent intentSetting;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentLists = new ArrayList<Fragment>();
    private ConstraintLayout mConstraintLayout;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout mPlayRelativeLayout;
    private Button mBtnFinish, mBtnSetting;
    private Intent mSearchIntent;
    private long exitTime = 0;
    private TabLayout mTabLayout;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawerlayout);
        initView();

    }
    private void initView(){
        initsetViewPager();
        initToolBar();
        initDrawerLayout();
        if(isMarshmallow()){
            solvePermission();
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_appmain_drawelayout);
        initFlow();
    }
    private void initToolBar(){
        ImageButton imageButton = (ImageButton) findViewById(R.id.id_btn_search);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSearchIntent == null) {
                    mSearchIntent = new Intent(MainActivity.this, SearchActivity.class);
                }
                startActivity(mSearchIntent);
            }
        });
        mTabLayout = (TabLayout) findViewById(R.id.id_appmain_toolbar_tabLayout);
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
    //toolbar设置

    private void initsetViewPager(){
        mViewPager = (ViewPager) findViewById(R.id.id_appmain_viewpager);
        if (mFragmentLists.size() <= 0){
            if (mFrafmentLeft != null){
                mFrafmentLeft = new AllBaseFragment("第二");
            }
            if (mChatFragment == null){
                mChatFragment = new ChatFragment();
            }
            mFragmentLists.add(new LeftRecyclerView());
            mFragmentLists.add(new FragmentMid());
            mFragmentLists.add(mChatFragment);
        }
        NetEasyViewPagerAdapter netEasyViewPagerAdapter = new NetEasyViewPagerAdapter(getSupportFragmentManager(),mFragmentLists);
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
    private boolean isMarshmallow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
    //申请权限
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void solvePermission(){
        List<String> permissionNeed = new ArrayList<>();
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    ,WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                ,READ_EXTERNAL_STORAGE_REQUEST_CODE);
            final List<String> permissionsList = new ArrayList<String>();
            if (!addPermission(permissionsList,Manifest.permission.READ_CONTACTS))
                permissionNeed.add("Read Contacts");
            if(!addPermission(permissionsList,Manifest.permission.WRITE_CONTACTS))
                permissionNeed.add("Write Contacts");
            if (permissionsList.size() > 0){
                if (permissionNeed.size() > 0 ){
                    String message = "你还需要的权限有" + permissionNeed.get((0));
                    for (int i = 0 ; i < permissionNeed.size() ; i++){
                        message = message+","+permissionNeed.get(i);
                    }
                    return;
                }
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                return;
            }

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.M)//应该是权限注解吧，不加的话会报错
    private boolean addPermission(List<String>permissionsList, String permission){

        if(checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
            permissionsList.add(permission);
        }
        else if (!shouldShowRequestPermissionRationale(permission)){
            return false;
        }
        return true;
    }//添加权限
    public void onRequstPermissonsResult(int requestCode,String[] permissions, int[] grantRsults){
        switch (requestCode){
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:{
                Map<String,Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION,PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS,PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_CONTACTS,PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i <permissions.length;i++){
                    perms.put(permissions[i],grantRsults[i]);
                }
                if (perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                    //s所有权限通过申请
                }
                else {
                    Toast.makeText(MainActivity.this,"一些权限需要申请",Toast.LENGTH_SHORT).show();
                }


            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantRsults);
        }

    }//权限获取核实功能
    private void initDrawerLayout(){
        mRoundedImageView = (RoundedImageView) findViewById(R.id.id_user_roundedImageView);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.id_appmain_drawelayout_left);
        ImageButton btnImage = (ImageButton) findViewById(R.id.id_appmain_btn_showleft);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mDrawerLayout.isDrawerOpen(mConstraintLayout)){
                    mDrawerLayout.openDrawer(mConstraintLayout);
                }
            }
        });
        mBtnSetting = (Button) findViewById(R.id.id_setting);
        mBtnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(intentSetting);
            }
        });
        final RelativeLayout mRelativeLayout = (RelativeLayout) findViewById(R.id.id_base_play);
        mBtnFinish = (Button) findViewById(R.id.id_finish);
        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
        initNavigationView();
    }
    private void ToastForMain(String toast){
        Toast.makeText(this,toast,Toast.LENGTH_SHORT).show();

    }
    private void initFlow(){
        MSONGTEXTVIEW = (TextView) findViewById(R.id.id_textview_songsinger);
        MPLAYERTEXTVIEW = (TextView) findViewById(R.id.id_textview_songName);
        MBTNPLAY = (ImageButton) findViewById(R.id.id_flow_play);
        MUSERIMAGE = (ImageView) findViewById(R.id.id_imageview_album);
        MBTNPLAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayService.play_list.size()>0){
                    t3.henu.left_library.GYB_solve.MainActivity.playBinder.setIsPlay();
                }
                else {
                    ToastForMain("播放列表为空！");
                }
                //播放

            }
        });



    }
    private void initNavigationView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.id_left_recyclerview);
        if (mRecyclerViewDatasList.size() <= 0){
            String[]text = getResources().getStringArray(R.array.appmain_drawerlayout_left_menu_itemNames);
            int []images = {R.mipmap.ic_launcher,R.drawable.icon_appmain_left_menu_talk,R.drawable.icon_appmain_left_menu_listen,R.drawable.icon_appmain_left_menu_drink,
                    R.drawable.icon_appmain_left_menu_talk,R.drawable.icon_appmain_left_menu_listen,R.drawable.icon_appmain_left_menu_drink,
                    R.drawable.icon_appmain_left_menu_talk,R.drawable.icon_appmain_left_menu_listen,R.drawable.icon_appmain_left_menu_drink,
                    R.drawable.icon_appmain_left_menu_talk,R.drawable.icon_appmain_left_menu_listen,R.drawable.icon_appmain_left_menu_drink,
                    R.drawable.icon_appmain_left_menu_talk,R.drawable.icon_appmain_left_menu_listen,R.drawable.icon_appmain_left_menu_drink,
                    R.drawable.icon_appmain_left_menu_talk,R.drawable.icon_appmain_left_menu_listen};
            for (int i =0 ;i<text.length ; i++){
                RecyclerViewData recyclerViewData = new RecyclerViewData(images[i] , text[i]);
                mRecyclerViewDatasList.add(recyclerViewData);
            }
        }




        RecyclerviewAdapter recyclerviewAdapter = new RecyclerviewAdapter(mRecyclerViewDatasList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if ( i == 11){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    final String[] array  = new String[]{"这是假的","10s","15s","20s","60s"};
                    builder.setTitle("请选择时长")
                            .setIcon(R.drawable.ic_search)
                            .setItems(array, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this,AlarmService.class);
                            //暂时使用系统音源
                            int time = 0;
                            switch (i){
                                case 0:{
                                    break;
                                }
                                case  1:{
                                    time = AlarmService.MDURATION = 10;
                                    break;
                                }
                                case 2:{
                                    time = AlarmService.MDURATION = 15;
                                    break;
                                }
                                case 3:{
                                    time = AlarmService.MDURATION = 20;
                                    break;
                                }
                                case 4:
                                    {
                                    time = AlarmService.MDURATION = 60;
                                        break;
                                }

                            }//startService
                            if (time == 0){
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                },time*1000);//定时功能
                            }
                        }
                    }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else if (i!=0 && i!=5 && i!=8){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!MISLOG){
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                ToastForMain("已登陆了！");
                            }
                        }
                    },500);//登录功能
                }
            }
        });
        recyclerviewAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()){
                    case R.id.id_appmain_drawelayout_left_btn_signin:
                        Intent intent;
                        if (!MISLOG){
                            if (view.getId() == R.id.id_appmain_drawelayout_left_btn_signin){
                                mBtnLog = (Button) view;
                            }
                            intent = new Intent(MainActivity.this,LoginActivity.class);
                        }else {
                            intent = new Intent(MainActivity.this, MainActivity_XPD.class);
                        }
                        startActivity(intent);
                        break;
                }
            }
        });
        mRecyclerView.setAdapter(recyclerviewAdapter);

    }




}
