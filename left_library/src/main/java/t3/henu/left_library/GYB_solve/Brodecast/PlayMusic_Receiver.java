package t3.henu.left_library.GYB_solve.Brodecast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.List;

import t3.henu.left_library.GYB_solve.Activities.NetWork.MusicNetWork;
import t3.henu.left_library.GYB_solve.Activities.SongInfo;
import t3.henu.left_library.GYB_solve.All_View;
import t3.henu.left_library.GYB_solve.Collect;
import t3.henu.left_library.GYB_solve.PlayActivity;
import t3.henu.left_library.R;
import t3.henu.left_library.GYB_solve.Services.PlayService;

public class PlayMusic_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Bundle bun=intent.getBundleExtra("Bunde");
        final SongInfo songInfo = bun.getParcelable("songinfo");
        final RequestQueue mQueue = MusicNetWork.getmRequestqueue(context);
        boolean status= (boolean) intent.getExtras().get("play_status");
        if(PlayActivity.text_song_singer!=null){
            PlayActivity.text_song_singer.setText(songInfo.getSinger()+"--"+songInfo.getAlbum());
            PlayActivity.text_song_name.setText(songInfo.getSong());
        }
        MediaPlayer media= PlayService.mediaPlayer;

        if(PlayActivity.playingPlay!=null){
            if(media.isPlaying()&&!PlayActivity.is_use){
                long current=media.getCurrentPosition();
                long total=media.getDuration();
                String s1=getTime(current);
                String s2=getTime(total);
                PlayActivity.cp_time.setText(s1);
                PlayActivity.total_time.setText(s2);
                PlayActivity.seecbar.setMax((int) total);
                PlayActivity.seecbar.setProgress((int) current);
            }
            PlayActivity.playingPlay.setImageResource(status?R.drawable.ic_pause:R.drawable.ic_play);
        }
        if(PlayActivity.album_imageview!=null){
            Bitmap bitmap = SongInfo.getBitMap(context, (int) songInfo.getAlbumId());
            if (bitmap != null) {
                PlayActivity.album_imageview.setImageBitmap(bitmap);
            }else if(songInfo.getPucUrl()!=null){

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {

                        ImageRequest imageRequest=new ImageRequest(songInfo.getPucUrl(), new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                PlayActivity.album_imageview.setImageBitmap(bitmap);
                            }
                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                PlayActivity.album_imageview.setImageResource(R.drawable.huge);

                            }
                        });mQueue.add(imageRequest);
                    }
                });
            }
        }

        List<All_View> list= Collect.all_view_list;
        for(int i=0;i<list.size();i++){
            final All_View all_view=list.get(i);
            if(all_view.getSinger()!=null){
                all_view.getSinger().setText(songInfo.getSinger());
                all_view.getSong().setText(songInfo.getSong());
                //Toast.makeText(context,"收到广播："+(status),Toast.LENGTH_SHORT).show();
                if(status==false){

                    all_view.getImageButton().setImageResource(t3.henu.left_library.R.drawable.icon_play1);
                }else{
                    all_view.getImageButton().setImageResource(t3.henu.left_library.R.drawable.icon_pause);
                }
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = SongInfo.getBitMap(context, (int) songInfo.getAlbumId());
                        if (bitmap != null) {
                            all_view.getImageView().setImageBitmap(bitmap);
                        }else{
                            ImageRequest imageRequest=new ImageRequest(songInfo.getPucUrl(), new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    if(  all_view.getImageView()!=null){
                                        all_view.getImageView().setImageBitmap(bitmap);
                                    }
                                }
                            }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    all_view.getImageView().setImageResource(R.drawable.default_album);

                                }
                            });mQueue.add(imageRequest);
                        }

                    }
                });
                //Toast.makeText(context,(t3.henu.left_library.GYB_solve.MainActivity1.imageView==null)+"::"+songInfo.getPucUrl(),Toast.LENGTH_SHORT).show();

            }
        }



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
