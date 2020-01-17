package t3.henu.left_library.YYN_solve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import t3.henu.left_library.R;

/**
 * Created by hippo on 2017/6/1.
 */

public class MusicSongListAdapter extends BaseAdapter {
    private Context context;
    private List<Song> songs;
    private Song song;
    private ViewContainer vc;

    public MusicSongListAdapter(Context context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    public static void setListAdapter(Context context, List<Song> songs, ListView mMusicList) {
        MusicSongListAdapter musicSongListAdapter = new MusicSongListAdapter(context, songs);
        mMusicList.setAdapter(musicSongListAdapter);
    }

    @Override
    public int getCount() {
        return songs.size();
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
                    inflate(R.layout.music_song_item_yyn, null);
            vc.song_title = (TextView) convertView.findViewById(R.id.id_textView_song_name);
            vc.song_artist = (TextView) convertView.findViewById(R.id.id_textView_song_artist);

            convertView.setTag(vc);
        } else {
            vc = (ViewContainer) convertView.getTag();
        }
        Song song = songs.get(position);
        vc.song_title.setText(song.getTitle());
        vc.song_artist.setText(song.getArtist() + "-" + song.getAlbum());
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private class ViewContainer {
        public TextView song_title;
        public TextView song_artist;
    }

}
