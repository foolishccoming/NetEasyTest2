package t3.henu.left_library.GYB_solve.Activities.NetWork;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import t3.henu.left_library.YHQ_solve.utils.FileUtils;
import t3.henu.left_library.YHQ_solve.utils.ImageUtils;



class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private final String TAG="ResultAdapter";
    boolean isplaying=false;
    private List<SongInfo> mSongList = new ArrayList<>();
    public ResultAdapter(List<SongInfo> mMusicList)
    {
        this.mSongList=mMusicList;
    }

    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.yhq_view_holder_music,parent,false);
        final ResultAdapter.ViewHolder holder=new ResultAdapter.ViewHolder(view);
        holder.musicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position=holder.getAdapterPosition();
               // SongInfo songInfo=mSongList.get(position);//点击事件好
                //Toast.makeText(v.getContext(),songInfo.getPucUrl(),Toast.LENGTH_LONG).show();
                //Toast.makeText(parent.getContext(),position+songInfo.getSong()+songInfo.getSinger(),Toast.LENGTH_SHORT).show();
                if(mSongList!=null&&mSongList.size()>0){
                    if(mSongList.get(position).getPath()!=null){
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.playBinder.setPlayList(mSongList);
                                MainActivity.playBinder.setCurrent(position);
                                Log.d(TAG, "run: "+mSongList.get(position).getPath());
                            }
                        });

                    }else{
                        Toast.makeText(v.getContext(),"无法播放此歌曲！！",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(v.getContext(),"播放列表为空！！",Toast.LENGTH_LONG).show();
                }


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
    public void onBindViewHolder(ResultAdapter.ViewHolder holder, int position) {
        SongInfo onlineMusic=mSongList.get(position);
        //将从网络上获取的Sting 转化为图片
        ImageLoader.getInstance().displayImage(onlineMusic.getPucUrl(), holder.online_cover, ImageUtils.getCoverDisplayOptions());
        holder.online_title.setText(onlineMusic.getSong());
        String artist = FileUtils.getArtistAndAlbum(onlineMusic.getSinger(),onlineMusic.getAlbum());
        holder.online_artist.setText(artist);
        holder.online_divider.setVisibility(isShowDivider(position) ? View.VISIBLE : View.GONE);

    }

    private boolean isShowDivider(int position) {
        return position != mSongList.size() - 1;
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
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
