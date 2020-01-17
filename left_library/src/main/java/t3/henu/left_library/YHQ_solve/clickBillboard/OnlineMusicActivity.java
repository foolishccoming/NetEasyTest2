package t3.henu.left_library.YHQ_solve.clickBillboard;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import t3.henu.left_library.GYB_solve.Activities.NetWork.SearchActivity;
import t3.henu.left_library.GYB_solve.All_View;
import t3.henu.left_library.GYB_solve.Collect;
import t3.henu.left_library.GYB_solve.MainActivity;
import t3.henu.left_library.R;
import t3.henu.left_library.YHQ_solve.BillboardListInfo;
import t3.henu.left_library.YHQ_solve.OnlineMusic;
import t3.henu.left_library.YHQ_solve.OnlineMusicList;
import t3.henu.left_library.YHQ_solve.http.HttpCallback;
import t3.henu.left_library.YHQ_solve.http.HttpClient;
import t3.henu.left_library.YHQ_solve.utils.ImageUtils;


/**
 * Created by 117 on 2017/5/19.
 */

public class OnlineMusicActivity extends MainActivity {
    private static final int MUSIC_LIST_SIZE = 20;
    private ImageView online_header_bg,online_header_cover;
    private TextView online_header_title,online_header_update_date,online_header_comment;
    private RecyclerView online_recyclerView;
    private BillboardListInfo mListInfo;
    private OnlineMusicList mOnlineMusicList;
    private List<OnlineMusic> mMusicList = new ArrayList<>();
    private int mOffset = 0;
    private OnlineMusicAdapter mAdapter = new OnlineMusicAdapter(mMusicList);
    private  All_View all_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yhq_activity_online_music);
        mListInfo = (BillboardListInfo) getIntent().getSerializableExtra("music_list_type");
        setTitle(mListInfo.getTitle());
        init();
        onLoad();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        online_recyclerView.setLayoutManager(layoutManager);
        online_recyclerView.setAdapter(mAdapter);
        final GridLayoutManager manager = new GridLayoutManager(this,1);
        online_recyclerView.setLayoutManager(manager);
        online_recyclerView.setItemAnimator(new DefaultItemAnimator());
        online_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= manager.getItemCount() - 1){
                        onLoad();
                    }
                }
            }
        });
       all_view = new All_View(SearchActivity.t_singer, SearchActivity.t_songname,
                SearchActivity.imageView, SearchActivity.btn_play);
        Collect.addView(all_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Collect.removeView(all_view);
    }

    private void onLoad() {
        getMusic(mOffset);
    }

    private void init() {
        online_header_bg= (ImageView) findViewById(R.id.yhq_online_header_bg);
        online_header_cover= (ImageView) findViewById(R.id.yhq_online_header_cover);
        online_header_title= (TextView) findViewById(R.id.yhq_online_header_title);
        online_header_update_date= (TextView) findViewById(R.id.yhq_online_header_update_date);
        online_header_comment= (TextView) findViewById(R.id.yhq_online_header_comment);
        online_recyclerView= (RecyclerView) findViewById(R.id.yhq_online_recyclerView);
    }
    private void getMusic(final int offset) {
        HttpClient.getSongListInfo(mListInfo.getType(), MUSIC_LIST_SIZE, offset, new HttpCallback<OnlineMusicList>() {
            @Override
            public void onSuccess(OnlineMusicList response) {
                mOnlineMusicList = response;
                if (offset == 0 && response == null) {
                    return;
                } else if (offset == 0) {
                    initHeader();
                }
                if (response == null || response.getSong_list() == null || response.getSong_list().size() == 0) {
                    return;
                }
                mOffset += MUSIC_LIST_SIZE;
                mMusicList.addAll(response.getSong_list());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Exception e) {
                if (e instanceof RuntimeException) {
                    // 歌曲全部加载完成
                    return;
                }
                if (offset == 0) {
                } else {
                    Toast.makeText(getBaseContext(),"加载失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initHeader() {
        online_header_title.setText(mOnlineMusicList.getBillboard().getName());
        online_header_update_date.setText(getString(R.string.yhq_recent_update, mOnlineMusicList.getBillboard().getUpdate_date()));
        online_header_comment.setText(mOnlineMusicList.getBillboard().getComment());
        ImageSize imageSize = new ImageSize(200, 200);
        ImageLoader.getInstance().loadImage(mOnlineMusicList.getBillboard().getPic_s640(), imageSize,
                ImageUtils.getCoverDisplayOptions(), new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        online_header_cover.setImageBitmap(loadedImage);
                        online_header_bg.setImageBitmap(ImageUtils.blur(loadedImage));
                    }
                });
    }


}
