package t3.henu.left_library.YHQ_solve.clickBillboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.ArrayList;
import java.util.List;

import t3.henu.left_library.GYB_solve.Activities.SongInfo;
import t3.henu.left_library.GYB_solve.MainActivity;
import t3.henu.left_library.R;
import t3.henu.left_library.YHQ_solve.OnlineMusic;
import t3.henu.left_library.YHQ_solve.http.HttpCallback;
import t3.henu.left_library.YHQ_solve.http.HttpClient;
import t3.henu.left_library.YHQ_solve.utils.FileUtils;
import t3.henu.left_library.YHQ_solve.utils.ImageUtils;


/**
 * Created by 117 on 2017/5/20.
 */

public class OnlineMusicAdapter extends RecyclerView.Adapter<OnlineMusicAdapter.ViewHolder> {
    boolean isplaying = false;
    private List<OnlineMusic> mMusicList = new ArrayList<>();
    private List<SongInfo>mSongList=new ArrayList<>();//就他
    public OnlineMusicAdapter(List<OnlineMusic> mMusicList)
    {
        this.mMusicList=mMusicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.yhq_view_holder_music,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.musicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                SongInfo songInfo=mSongList.get(position);//点击事件好
                //Toast.makeText(parent.getContext(),position+songInfo.getSong()+songInfo.getSinger(),Toast.LENGTH_SHORT).show();
                MainActivity.playBinder.setPlayList(mSongList);
                MainActivity.playBinder.setCurrent(position);

            }
        });
        holder.online_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"点击了更多",Toast.LENGTH_LONG).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OnlineMusic onlineMusic=mMusicList.get(position);
        //将从网络上获取的Sting 转化为图片
        ImageLoader.getInstance().displayImage(onlineMusic.getPic_small(), holder.online_cover, ImageUtils.getCoverDisplayOptions());
        holder.online_title.setText(onlineMusic.getTitle());
        String artist = FileUtils.getArtistAndAlbum(onlineMusic.getArtist_name(), onlineMusic.getAlbum_title());
        holder.online_artist.setText(artist);
        holder.online_divider.setVisibility(isShowDivider(position) ? View.VISIBLE : View.GONE);

       //将网络歌曲信息本地化
        final SongInfo songInfo=new SongInfo();
        songInfo.setSong(onlineMusic.getTitle());//歌名
        songInfo.setId(Long.parseLong(onlineMusic.getSong_id()));//歌id
        songInfo.setSinger(artist);//歌手以及专辑
        songInfo.setLrc(onlineMusic.getLrclink());//歌词
        songInfo.setPucUrl(onlineMusic.getPic_big());
        songInfo.setPic_big(onlineMusic.getPic_big());//大图片
        songInfo.setPic_small(onlineMusic.getPic_small());//小图片
        // 获取歌曲播放链接
        HttpClient.getMusicDownloadInfo(onlineMusic.getSong_id(), new HttpCallback<DownloadInfo>() {
            @Override
            public void onSuccess(DownloadInfo response) {
                if (response == null || response.getBitrate() == null) {
                    onFail(null);
                    return;
                }
                songInfo.setPath(response.getBitrate().getFile_link());//歌的url
                songInfo.setDuration(response.getBitrate().getFile_duration());//歌的时间
            }
            @Override
            public void onFail(Exception e) {
            }
        });
        mSongList.add(songInfo);
    }

    private boolean isShowDivider(int position) {
        return position != mMusicList.size() - 1;
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView online_cover, online_more;
        View online_divider;
        TextView online_title, online_artist;
        View musicView;

        public ViewHolder(View view) {
            super(view);
            online_cover = (ImageView) view.findViewById(R.id.yhq_online_cover);
            online_more = (ImageView) view.findViewById(R.id.yhq_online_more);
            online_divider = view.findViewById(R.id.yhq_online_divider);
            online_title = (TextView) view.findViewById(R.id.yhq_online_title);
            online_artist = (TextView) view.findViewById(R.id.yhq_online_artist);
            musicView = view;
        }
    }
}
