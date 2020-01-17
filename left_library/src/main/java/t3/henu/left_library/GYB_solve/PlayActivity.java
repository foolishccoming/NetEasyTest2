package t3.henu.left_library.GYB_solve;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import t3.henu.left_library.GYB_solve.Services.PlayService;
import t3.henu.left_library.R;



public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    public static boolean is_use=false;
    public static ImageButton playingPre, playingPlay, playingNext, playStatus, playQueue;
    public static TextView cp_time,total_time;
    public static RoundedImageView album_imageview;
    public static SeekBar seecbar;
    public static TextView text_song_name,text_song_singer;
    public static int[] images = {R.drawable.play_icn_loop_prs, R.drawable.play_icn_shuffle,
            R.drawable.play_icn_one_prs};
    public static String[] texts = {"顺序播放", "随机播放", "单曲循环"};
    public ImageView disc, needle;
    private ObjectAnimator discAnimation,needleAnimation;
    private boolean isPlaying = true;
    private DialogAdapter mAdapter;
    private ImageButton imageButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyb_activity_play);
        initViews();
    }


    private void makeStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initViews() {
        makeStatusBarTransparent();
        playingPre = (ImageButton) findViewById(R.id.playing_pre);
        playingPlay = (ImageButton) findViewById(R.id.playing_play);
        playingNext = (ImageButton) findViewById(R.id.playing_next);
        playQueue = (ImageButton) findViewById(R.id.id_btn_show_liebiao);
        album_imageview= (RoundedImageView) findViewById(R.id.imageView_roundedImageView);
        text_song_name= (TextView) findViewById(R.id.text_play_song);
        text_song_singer=(TextView)findViewById(R.id.text_play_singer);
        playStatus= (ImageButton) findViewById(R.id.id_btn_change_status);
        playingPre.setOnClickListener(this);
        playingPlay.setOnClickListener(this);
        playingNext.setOnClickListener(this);
        playQueue.setOnClickListener(this);
        seecbar= (SeekBar) findViewById(R.id.musicSeekBar);
        seecbar.setMax(100);
        seecbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                long curent=progress;
                cp_time.setText(getTime(curent));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                PlayService.mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        int st= PlayService.status;
        playStatus.setImageResource(images[st - 1]);
        imageButton= (ImageButton) findViewById(R.id.id_play_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cp_time= (TextView) findViewById(R.id.id_tv_cp_time);
        total_time= (TextView) findViewById(R.id.id_tv_total_time);
        playStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int st= PlayService.status;
                st=(st+1)%images.length;
                playStatus.setImageResource(images[st]);
                MainActivity.playBinder.setPlayStatus(st+1);
                toast(texts[st]);
            }
        });
    }


    private void toast(String text) {
        Toast.makeText(PlayActivity.this,text,Toast.LENGTH_SHORT).show();
    }

    //动画设置
    private void setAnimations() {

        discAnimation = ObjectAnimator.ofFloat(disc, "rotation", 0, 360);
        discAnimation.setDuration(20000);
        discAnimation.setInterpolator(new LinearInterpolator());
        discAnimation.setRepeatCount(ValueAnimator.INFINITE);

        needleAnimation = ObjectAnimator.ofFloat(needle, "rotation", 0, 25);
        needle.setPivotX(0);
        needle.setPivotY(0);
        needleAnimation.setDuration(800);
        needleAnimation.setInterpolator(new LinearInterpolator());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.playing_pre) {
            MainActivity.playBinder.pre();
            //播放中
        } else if (id == R.id.playing_play) {
            MainActivity.playBinder.setIsPlay();
            //下一曲
        } else if (id == R.id.playing_next) {
            MainActivity.playBinder.next();

        } else if (id == R.id.id_btn_show_liebiao) {
            showPlayList();

        }
    }

    public void showPlayList() {
        final Dialog dialog = new Dialog(PlayActivity.this, R.style.ActionSheetDialogStyle);//填充对话框的布局
        View inflate = LayoutInflater.from(PlayActivity.this).inflate(R.layout.yhq_music_dialog, null);//初始化控件
        final ImageView dialogStatus = (ImageView) inflate.findViewById(R.id.dialog_status);
        final TextView textStatus = (TextView) inflate.findViewById(R.id.musicdialog_status);
        LinearLayout status = (LinearLayout) inflate.findViewById(R.id.dialog_linearlayout1);
        ListView listView = (ListView) inflate.findViewById(R.id.musicdialog_list);

        mAdapter = new DialogAdapter(PlayService.play_list, this, PlayService.current);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        int st = PlayService.status;
        dialogStatus.setImageResource(images[st - 1]);
        textStatus.setText(texts[st - 1]);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayService.status = (PlayService.status + 1) % 4;
                if (PlayService.status == 0) PlayService.status = 1;
                int st = PlayService.status;
                dialogStatus.setImageResource(images[st - 1]);
                textStatus.setText(texts[st - 1]);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                MainActivity.playBinder.setCurrent(position);
                mAdapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setCurrent(position);
                        mAdapter.notifyDataSetChanged();
                    }
                }, 1000);

            }
        });


        dialog.setContentView(inflate);//将布局设置给Dialog
        Window dialogWindow = dialog.getWindow();//获取当前Activity所在的窗体
        dialogWindow.setGravity(Gravity.BOTTOM);//设置Dialog从窗体底部弹出
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();//获得窗体的属性
        lp.y = 20;//设置Dialog距离底部的距离
        lp.width = (int) getResources().getDisplayMetrics().widthPixels;
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    private String getTime(long current) {
        String s1="",s2="";
        long min=current/1000/60;
        long second=current/1000%60;
        if(min<10){
            s1="0";
        }
        s1+=min;
        if(second<10){
            s2="0";
        }
        s2+=second;
        return s1+":"+s2;
    }

}
