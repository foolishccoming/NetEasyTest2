package t3.henu.left_library.YYN_solve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import t3.henu.left_library.R;

/**
 * Created by hippo on 2017/5/25.
 */

public class MusicSingerListAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> csongs;
    private Song song;
    private ViewContainer vc;

    public MusicSingerListAdapter(Context context, List<Map<String, Object>> csongs) {
        this.context = context;
        this.csongs = csongs;
    }

    public static void setListAdapter(Context context, List<Map<String, Object>> csongs, ListView mMusicList) {
        MusicSingerListAdapter musicSingeListAdapter = new MusicSingerListAdapter(context, csongs);
        mMusicList.setAdapter(musicSingeListAdapter);
    }

    @Override
    public int getCount() {
        return csongs.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        vc = null;
        if (convertView == null) {
            vc = new ViewContainer();
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.music_singer_item, null);
            vc.song_artist = (TextView) convertView.findViewById(R.id.id_textView_singer_name);
            vc.song_count = (TextView) convertView.findViewById(R.id.id_textView_singer_scount);
            vc.song_artistpic = (ImageView) convertView.findViewById(R.id.id_imageView_singer_pic);

            convertView.setTag(vc);
        } else {
            vc = (ViewContainer) convertView.getTag();
        }

        Map<String, Object> map = csongs.get(position);

        vc.song_count.setText(map.get("COUNT").toString() + "首");
        vc.song_artist.setText(map.get("ARTIST").toString());
        int artistid = (int) map.get("ARTIST_ID");


        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private class ViewContainer {
        public TextView song_count;
        public TextView song_artist;
        public ImageView song_artistpic;
    }

}
