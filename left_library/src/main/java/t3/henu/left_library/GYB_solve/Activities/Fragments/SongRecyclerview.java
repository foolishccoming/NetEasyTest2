package t3.henu.left_library.GYB_solve.Activities.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import t3.henu.left_library.GYB_solve.Activities.MusicUtils;
import t3.henu.left_library.GYB_solve.Activities.SongInfo;
import t3.henu.left_library.GYB_solve.MainActivity;
import t3.henu.left_library.R;
public class SongRecyclerview extends Fragment {
    private static final int READ_SMS_REQUEST_CODE = 125;
    final private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 122;
    final private int READ_EXTERNAL_STORAGE_REQUEST_CODE = 123;
    SongRecyclerviewAdapter adapter;
    private View rootView;
    private RecyclerView recyclerView;
    private List<SongInfo> listsong = new ArrayList<SongInfo>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gyb_fragment_music_info, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.id_recyclerview_songs);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        });

        return rootView;
    }


    private void initView() {
        if (listsong.size() <= 0) {
            listsong = MusicUtils.getMp3Infos(getContext());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SongRecyclerviewAdapter(listsong, getContext());
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        View header = LayoutInflater.from(getContext()).inflate(R.layout.gyb_music_song_item_header, null, false);
        adapter.addHeaderView(header);
        recyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(final View view, int i) {
                //toast(listsong.get(i).getSong() + ",路径：" + listsong.get(i).path);
                SongInfo song = MusicUtils.list.get(i);
                if (song.getAlbum_bitmap() == null) {
                    Bitmap bitmap = SongInfo.getBitMap(getContext(), (int) song.getAlbumId());
                    listsong.get(i).setAlbum_bitmap(bitmap);
                    //toast(String.valueOf(bitmap == null));
                }

                adapter.notifyDataSetChanged();
                MainActivity.playBinder.setPlayList(listsong);
                MainActivity.playBinder.setCurrent(i);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageView imageView = (ImageView) view.findViewById(R.id.id_songinfo_isplay);
                        imageView.setVisibility(View.VISIBLE);
                    }
                }, 1000);

            }
        });

        adapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                int i1 = view.getId();
                if (i1 == R.id.id_songinfo_mv) {
                    toast("mv:" + i);

                } else if (i1 == R.id.id_songinfo_more) {
                    toast("更多:" + i);
                }

            }
        });

    }

    private void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
