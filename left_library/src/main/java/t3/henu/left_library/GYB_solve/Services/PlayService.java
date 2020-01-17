package t3.henu.left_library.GYB_solve.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import t3.henu.left_library.GYB_solve.Activities.SongInfo;

public class PlayService extends Service {
    public final static String RECiEVE1="gyb.ne.play_music.play";
    public static MediaPlayer mediaPlayer;
    public static List<SongInfo>play_list;//当前播放的列表
    public static int status=1;//1代表顺序循环，2代表随机循环，3代表单曲循环
    public static boolean isplay=false;//是否播放
    public static int current = 0;//当前播放的歌曲序号
    private playBinder binder=new playBinder();

    public PlayService() {
    }

    private void pre_song() {
        if(play_list.size()>0){
            if(status==1||status==3){
                current = current - 1;
                if(current<0){
                    current=play_list.size()-1;
                }
            } else if (status == 2) {
                current=(int)new Random().nextInt(play_list.size());
            }
            play(0);
        }
    }

    @Override
    public void onCreate() {
        init();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        sendBroad();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();

        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        sendBroad();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        if(mediaPlayer==null){
            mediaPlayer=new MediaPlayer();
        }
        play_list=new LinkedList<>();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                next_song();
            }
        });
    }

    private void next_song() {
        if(play_list.size()>0){
          if(status==1||status==3){
                current=(current+1)%play_list.size();
            }else if(status==2){
                current=new Random().nextInt(play_list.size());
            }

            play(0);
        }
    }

    /**
     * 播放音乐
     *
     * @param
     */
    private void play(final int currentTime) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(play_list!=null&&play_list.size()>0){
                    try {
                        isplay=true;
                        SongInfo songInfo=play_list.get(current);
                        //sendBroad();
                        mediaPlayer.reset();// 把各项参数恢复到初始状态
                        mediaPlayer.setDataSource(songInfo.getPath());
                        mediaPlayer.prepare(); // 进行缓冲
                        mediaPlayer.setOnPreparedListener(new PreparedListener(currentTime));// 注册一个监听器
                        // toast(current+":"+play_list.get(current).path);
                    } catch (Exception e) {
                        toast("播放错误");
                        e.printStackTrace();
                    }
                }else{
                    toast("播放列表为空");
                }
            }
        });


    }

    private void toast(String s) {
        Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();
    }

    /**
     * 暂停音乐
     */
    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            //MainActivity1.btn_play.setImageResource(R.drawable.icon_play1);
            isplay = false;
            sendBroad();
        }
    }

    public  void sendBroad() {
        if(play_list.size()>0){
            Intent in=new Intent();
            in.putExtra("play_status",isplay);
            if(play_list.size()>0){
                Bundle bund=new Bundle();
                bund.putParcelable("songinfo",play_list.get(current));
                in.putExtra("Bunde",bund);
            }

            in.setAction(RECiEVE1);
            sendBroadcast(in);
        }

    }

    private void resume() {
        if (!isplay) {
            //  MainActivity1.btn_play.setImageResource(R.drawable.icon_pause);
            isplay = true; sendBroad();
            mediaPlayer.start();
            //play(0);
        }
    }

    /**
     * 停止音乐
     */
    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public class playBinder extends Binder {
        public void setPlayStatus(int s) {
            status = s;
        }

        public void setPlayList(List<SongInfo> list) {
            play_list = list;
        }

        public void setIsPlay() {
            if (isplay == true) {
                //toast("停止");
                pause();
            } else { //toast("开始");
                resume();
            }
        }

        public void setCurrent(int curr) {
            current = curr;
            play(0);
        }

        public void next() {
            next_song();
        }

        public void pre() {
            pre_song();
        }
    }

    private final class PreparedListener implements MediaPlayer.OnPreparedListener {
        private int currentTime;

        public PreparedListener(int currentTime) {
            this.currentTime = currentTime;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start(); // 开始播放
            if (currentTime > 0) { // 如果音乐不是从头播放
                mediaPlayer.seekTo(currentTime);
            }

        }
    }
}
