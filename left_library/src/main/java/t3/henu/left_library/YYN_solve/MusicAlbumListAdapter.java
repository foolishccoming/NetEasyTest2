package t3.henu.left_library.YYN_solve;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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

public class MusicAlbumListAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> csongs;
    private Song song;
    private ViewContainer vc;

    public MusicAlbumListAdapter(Context context, List<Map<String, Object>> csongs) {
        this.context = context;
        this.csongs = csongs;
    }

    public static void setListAdapter(Context context, List<Map<String, Object>> csongs, ListView mMusicList) {
        MusicAlbumListAdapter musicAlbumListAdapter = new MusicAlbumListAdapter(context, csongs);
        mMusicList.setAdapter(musicAlbumListAdapter);
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
                    inflate(R.layout.music_album_item, null);
            vc.song_count = (TextView) convertView.findViewById(R.id.id_textView_album_scount);
            vc.song_album = (TextView) convertView.findViewById(R.id.id_textView_album_name);
            vc.song_artist = (TextView) convertView.findViewById(R.id.id_textView_album_singer);
            vc.song_albumpic = (ImageView) convertView.findViewById(R.id.id_imageView_album_pic);

            convertView.setTag(vc);
        } else {
            vc = (ViewContainer) convertView.getTag();
        }

        Map<String, Object> map = csongs.get(position);

        vc.song_count.setText(map.get("COUNT").toString() + "首");
        vc.song_album.setText(map.get("ALBUM").toString());
        vc.song_artist.setText(map.get("ARTIST").toString());
        int album_id = (int) map.get("ALBUM_ID");


        String albumArt = getAlbumArt(album_id);
        Bitmap bm = null;
        if (albumArt != null) {
            bm = BitmapFactory.decodeFile(albumArt);
            BitmapDrawable bmpDraw = new BitmapDrawable(bm);
            vc.song_albumpic.setImageDrawable(bmpDraw);
        }
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private String getAlbumArt(int album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = context.getContentResolver().query(
                Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),
                projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        return album_art;
    }

    private class ViewContainer {
        public TextView song_count;
        public TextView song_album;
        public TextView song_artist;
        public ImageView song_albumpic;
    }

}
