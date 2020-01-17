package t3.henu.left_library.GYB_solve.Activities.NetWork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import t3.henu.left_library.GYB_solve.Activities.MusicInfo.*;
import t3.henu.left_library.GYB_solve.Activities.SongInfo;
import t3.henu.left_library.GYB_solve.All_View;
import t3.henu.left_library.GYB_solve.Collect;
import t3.henu.left_library.GYB_solve.MainActivity;
import t3.henu.left_library.R;

import static t3.henu.left_library.YHQ_solve.AppCache.getContext;

public class SearchResult extends MainActivity {
    private static final int MUSIC_LIST_SIZE = 20;
    final String TAG = "SearchResult";
    String music_name;
    List<Song> get_songs;
    private String music_id;
    private  int offset=0;
    private RecyclerView recyclerView;
    private MusicResponse music;
    private int mOffset = 0;
    private List<SongInfo> songs=new ArrayList<SongInfo>();
    private ResultAdapter resultAdapter;
    public MusicNetWork.VolleyCallback volleyCallback=new MusicNetWork.VolleyCallback() {
        @Override
        public void onSuccess(String result) {
            Gson gson=new Gson();
            music=gson.fromJson(result,MusicResponse.class);
            if(music.getResult()==null||music.getResult().getSongCount()<=0){
                return;
            }
            get_songs=music.getResult().getSongs();
            Log.d(TAG, "onSuccess: "+result);
            List<SongInfo> li=new ArrayList<SongInfo>();
            for(int i=0;i<get_songs.size();i++){
                SongInfo info=new SongInfo();
                Song s=get_songs.get(i);
                info.setSong(s.getName());
                info.setSinger(s.getArtists().get(0).getName());
                info.setPath(s.getAudio()); Album album=s.getAlbum();
                info.setAlbum(album.getName());
                if(s.getPicUrl()!=null){
                    info.setPucUrl(s.getPicUrl());
                }else if(s.getArtists().get(0).getPicUrl()!=null){
                    info.setPucUrl(s.getArtists().get(0).getPicUrl());
                }else {

                    info.setPucUrl(album.getPicUrl());
                }
                li.add(info);
            }
            songs.addAll(li);
            mOffset+=MUSIC_LIST_SIZE;
            //toast(songs.size()+"");
            resultAdapter.notifyDataSetChanged();
        }
    };
    private All_View all_view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyb_activity_search_result);
        Intent intent = getIntent();
        music_name = intent.getStringExtra("music_name");
        music_id = intent.getStringExtra("music_id");
        initView();//
        all_view = new All_View(SearchResult.t_singer, SearchResult.t_songname,
                SearchResult.imageView, SearchResult.btn_play);
        Collect.addView(all_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Collect.removeView(all_view);
    }

    private void onLoad( int Offset) {
        MusicNetWork.SearchMusic(getContext(), music_name, MUSIC_LIST_SIZE, 1, mOffset,volleyCallback );
       /* MusicNetWork.Cloud_Music_MusicInfoAPI(getContext(), music_id, music_id, new MusicNetWork.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                toast(mOffset+result);
            }
        });*/
    }

    private void initView() {
        findViewById(R.id.id_result_btn_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView= (RecyclerView) findViewById(R.id.id_recyclerview_result);
       // View header= LayoutInflater.from(getContext()).inflate(R.layout.music_song_item_header,null,false);
        onLoad(mOffset);
        resultAdapter=new ResultAdapter(songs);
        //resultAdapter.addHeaderView(header);
        final GridLayoutManager manager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(resultAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= manager.getItemCount() - 1){
                        //offset+=MUSIC_LIST_SIZE;
                        onLoad(mOffset);
                    }
                }
            }
        });
    }

    private void toast(String s) {
        Toast.makeText(SearchResult.this,s,Toast.LENGTH_LONG).show();
    }




}
