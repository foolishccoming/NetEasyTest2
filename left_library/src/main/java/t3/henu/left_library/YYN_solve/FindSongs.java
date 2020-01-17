package t3.henu.left_library.YYN_solve;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

/**
 * Created by 202 on 2017/5/20.
 */

public class FindSongs {
    private DBAdapter dbAdapter;

    public FindSongs(DBAdapter _dbAdapter) {
        dbAdapter = _dbAdapter;
    }

    public void getSongsIntoDB(ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        for (int i = 0; i < cursor.getCount(); i++) {
            Song song = new Song();
            cursor.moveToNext();
            long song_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String song_title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String song_artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            int song_artistid = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
            String song_album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            int song_albumid = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            String song_uri = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            if (isMusic != 0) {
                song.setId(song_id);
                if (song_title == null)
                    song.setTitle("未知艺术家");
                else
                    song.setTitle(song_title);
                song.setArtist(song_artist);
                song.setArtistid(song_artistid);
                song.setAlbum(song_album);
                song.setAlbumid(song_albumid);
                song.setUri(song_uri);
                dbAdapter.insert(song);
            }
        }
        cursor.close();
    }
}
