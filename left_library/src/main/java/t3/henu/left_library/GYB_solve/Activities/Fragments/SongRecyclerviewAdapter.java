package t3.henu.left_library.GYB_solve.Activities.Fragments;

import android.content.Context;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import t3.henu.left_library.GYB_solve.Activities.SongInfo;
import t3.henu.left_library.R;

/**
 * Created by   on 2017/4/14.
 */

public class SongRecyclerviewAdapter extends BaseQuickAdapter<SongInfo> {
    private  Context context;
    public SongRecyclerviewAdapter(List<SongInfo> data ,Context context) {
        super(R.layout.gyb_music_song_item, data);
        this.context=context;
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    private void toast(String s) {
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, SongInfo songInfo) {
        baseViewHolder.setText(R.id.id_songinfo_author,songInfo.getSinger());
        baseViewHolder.setText(R.id.id_songinfo_name,songInfo.getSong());
        baseViewHolder.setOnClickListener(R.id.id_songinfo_more,new OnItemChildClickListener());
        baseViewHolder.setOnClickListener(R.id.id_songinfo_mv,new OnItemChildClickListener());
    }
}
