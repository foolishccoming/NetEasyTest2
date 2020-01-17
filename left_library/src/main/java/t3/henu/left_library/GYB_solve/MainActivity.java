package t3.henu.left_library.GYB_solve;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import t3.henu.left_library.GYB_solve.Services.PlayService;
import t3.henu.left_library.R;


public class MainActivity extends AppCompatActivity {
    public static ImageButton btn_play,btn_menu;
    public static ImageView imageView;
    public static TextView t_songname,t_singer;
    public static PlayService.playBinder playBinder=null;
    public static ServiceConnection con=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playBinder= (PlayService.playBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    protected Context mContext;
    private FrameLayout mContentContainer;
    private View mFloatView;
    private PopWindowMenu popWindowMenu;
    private RelativeLayout play_layout;
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mContext = this;
        ViewGroup mDecorView = (ViewGroup) getWindow().getDecorView();
        mContentContainer = (FrameLayout) ((ViewGroup) mDecorView.getChildAt(0)).getChildAt(1);
        mFloatView = LayoutInflater.from(getBaseContext()).inflate(R.layout.gyb_flowplaymusic, null);
        btn_menu= (ImageButton) mFloatView.findViewById(R.id.id_btn_liebiao);
        btn_play = (ImageButton) mFloatView.findViewById(R.id.id_flow_play);
        t_singer= (TextView) mFloatView.findViewById(R.id.id_textview_songsinger);
        t_songname=(TextView) mFloatView.findViewById(R.id.id_textview_songName);
        imageView= (ImageView) mFloatView.findViewById(R.id.id_imageview_album);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"播放",Toast.LENGTH_LONG).show();
                MainActivity.playBinder.setIsPlay();
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getBaseContext(), R.style.ActionSheetDialogStyle);//填充对话框的布局
                View inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.yhq_music_dialog, null);//初始化控件
                final ImageView dialogStatus = (ImageView) inflate.findViewById(R.id.dialog_status);
                final TextView textStatus = (TextView) inflate.findViewById(R.id.musicdialog_status);
                LinearLayout status = (LinearLayout) inflate.findViewById(R.id.dialog_linearlayout1);
                ListView listView = (ListView) inflate.findViewById(R.id.musicdialog_list);

                final DialogAdapter mAdapter = new DialogAdapter(PlayService.play_list, getBaseContext(), PlayService.current);
                listView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                int st = PlayService.status;
                dialogStatus.setImageResource(PlayActivity.images[st - 1]);
                textStatus.setText(PlayActivity.texts[st - 1]);
                status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayService.status = (PlayService.status + 1) % 4;
                        if (PlayService.status == 0) PlayService.status = 1;
                        int st = PlayService.status;
                        dialogStatus.setImageResource(PlayActivity.images[st - 1]);
                        textStatus.setText(PlayActivity.texts[st - 1]);
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
                toast("菜单");
            }
        });
        play_layout = (RelativeLayout)mFloatView.findViewById(R.id.id_base_play);
        play_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PlayActivity.class));
            }
        });
    }

    private void toast(String s) {
        Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity= Gravity.BOTTOM;
        mContentContainer.addView(mFloatView, layoutParams);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     *
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        // intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        super.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(0, 0);
    }
}
