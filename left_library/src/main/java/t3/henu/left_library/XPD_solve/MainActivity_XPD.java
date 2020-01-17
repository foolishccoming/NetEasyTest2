package t3.henu.left_library.XPD_solve;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import t3.henu.left_library.R;

public class MainActivity_XPD extends FragmentActivity implements View.OnClickListener {
    private Button btn_ziliao;
    private TextView yinyue, dongtai, guanyuwo;
    private ViewPager viewPager;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    private Fragment F1, F2, F3, F4, F5;
    private de.hdodenhof.circleimageview.CircleImageView icon_image;
    private LinearLayout zhujiemian;
    private TextView name;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.fenxiang) {
            Toast.makeText(this, "你点击了分享", Toast.LENGTH_SHORT).show();

        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_xpd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.fanhui);
        btn_ziliao = (Button) findViewById(R.id.ziliao);
        btn_ziliao.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        yinyue = (TextView) findViewById(R.id.yinyue);
        dongtai = (TextView) findViewById(R.id.dongtai);
        guanyuwo = (TextView) findViewById(R.id.guanyuwo);
        zhujiemian = (LinearLayout) findViewById(R.id.zhujiemian);
        name = (TextView) findViewById(R.id.name);

        //接收bitmap数据
        icon_image = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.icon_image);
        Intent intent = getIntent();
        try {
            if (intent != null) {
                byte[] bis = intent.getByteArrayExtra("bitmap");
                Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
                icon_image.setImageBitmap(bitmap);

                byte[] bis1 = intent.getByteArrayExtra("bitmap1");
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(bis1, 0, bis1.length);
                BitmapDrawable bd = new BitmapDrawable(bitmap1);
                zhujiemian.setBackgroundDrawable(bd);


                String Name = intent.getStringExtra("name");
                name.setText(Name);

            }
        } catch (Exception e) {

        }


//        public void saveMyBitmap(String bitName,Bitmap mBitmap) throws IOException {
//            File f = new File("/sdcard/Note/" + bitName);
//            if(!f.exists())
//                f.mkdirs();//如果没有这个文件夹的话，会报file not found错误
//            f=new File("/sdcard/Note/"+bitName+".png");
//            f.createNewFile();
//            try {
//                FileOutputStream out = new FileOutputStream(f);
//                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                out.flush();
//                out.close();
//            } catch (FileNotFoundException e) {
//                Log.i(TAG,e.toString());
//            }
//
//        }


        mFragments = new ArrayList<Fragment>();
        Fragment F1 = new Yinyue();
        Fragment F2 = new Dongtai();
        Fragment F3 = new Guanyuewo();
        mFragments.add(F1);
        mFragments.add(F2);
        mFragments.add(F3);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                //return mFragments.getitem(position);
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        viewPager.setAdapter(mAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = viewPager.getCurrentItem();
                setItem(currentItem);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        initEvent();
        //   viewPager.setCurrentItem(0);
        setSelect(0);

        //对NavigationIcon添加点击
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    private void initEvent() {
        yinyue.setOnClickListener(this);
        dongtai.setOnClickListener(this);
        guanyuwo.setOnClickListener(this);
    }

    private void setItem(int i) {
        reImages();
        switch (i) {
            case 0:
                // home.setImageResource(R.mipmap.home2);
                yinyue.setTextColor(this.getResources().getColor(R.color.text));
                break;
            case 1:
                //mess.setImageResource(R.mipmap.mess2);
                dongtai.setTextColor(this.getResources().getColor(R.color.text));
                break;
            case 2:
                //people.setImageResource(R.mipmap.people2);
                guanyuwo.setTextColor(this.getResources().getColor(R.color.text));
                break;
        }
    }

    private void setSelect(int i) {
        setItem(i);
        viewPager.setCurrentItem(i);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ziliao) {
            Intent intent = new Intent(MainActivity_XPD.this, Main2Activity.class);
            startActivity(intent);
            setSelect(0);
            finish();
        } else if (i == R.id.yinyue) {
            setSelect(0);

        } else if (i == R.id.dongtai) {
            setSelect(1);

        } else if (i == R.id.guanyuwo) {
            setSelect(2);

        }
    }

    private void reImages() {
//        home.setImageResource(R.mipmap.home1);
//        mess.setImageResource(R.mipmap.mess1);
//        people.setImageResource(R.mipmap.people1);
//        find.setImageResource(R.mipmap.find1);
//        point.setImageResource(R.mipmap.point1);
        yinyue.setTextColor(this.getResources().getColor(R.color.yuan));
        dongtai.setTextColor(this.getResources().getColor(R.color.yuan));
        guanyuwo.setTextColor(this.getResources().getColor(R.color.yuan));
    }
}
