package t3.henu.left_library.YYN_solve;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 202 on 2017/5/25.
 */

public class DBAdapter {
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ARTIST = "artist";
    public static final String KEY_ALBUM = "album";
    public static final String KEY_ALBUMID = "albumid";
    public static final String KEY_ARTISTID = "artistid";
    public static final String KEY_URI = "uri";
    private static final String DB_NAME = "song.db";
    private static final String DB_TABLE = "songinfo2";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    private Context context;
    private DBOpenHelper dbOpenHelper;

    public DBAdapter(Context _context) {
        context = _context;
    }

    public void open() throws SQLiteException {
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public long insert(Song song) {
        ContentValues newValues = new ContentValues();

        newValues.put(KEY_ID, song.getId());
        newValues.put(KEY_TITLE, song.getTitle());
        newValues.put(KEY_ARTIST, song.getArtist());
        newValues.put(KEY_ALBUM, song.getAlbum());
        newValues.put(KEY_ARTISTID, song.getArtistid());
        newValues.put(KEY_ALBUMID, song.getAlbumid());
        newValues.put(KEY_URI, song.getUri());

        return db.insert(DB_TABLE, null, newValues);
    }

    public List<Map<String, Object>> queryPartData(String flag) {
        Cursor group_songs = null;
        Cursor flag_songs = null;
        List<Map<String, Object>> csongs = new ArrayList<>();
        Map<String, Object> map;

        switch (flag) {
            case "SINGER":

                flag_songs = db.rawQuery("select " + KEY_ARTIST + "," + KEY_ARTISTID
                        + " from " + DB_TABLE
                        + " group by " + KEY_ARTIST, null);

                while (flag_songs.moveToNext()) {
                    map = new HashMap<>();
                    String artist = flag_songs.getString(0);
                    int artistid = flag_songs.getInt(1);

                    group_songs = db.query(DB_TABLE, new String[]{
                                    KEY_ID, KEY_TITLE, KEY_ARTIST, KEY_ALBUM, KEY_ARTISTID},
                            KEY_ARTIST + "= ?", new String[]{artist}, null, null, null);
                    List<Song> songs = new ArrayList<>();

                    while (group_songs.moveToNext()) {
                        Song song = new Song();
                        song.setId(group_songs.getInt(0));
                        song.setTitle(group_songs.getString(1));
                        song.setArtist(group_songs.getString(2));
                        song.setAlbum(group_songs.getString(3));
                        song.setArtistid(group_songs.getInt(4));
                        songs.add(song);
                    }
                    map.put("ARTIST", artist);
                    map.put("COUNT", songs.size());
                    map.put("ARTIST_ID", artistid);
                    map.put("SONGLIST", songs);
                    csongs.add(map);
                }

                break;
            case "ALBUM":

                flag_songs = db.rawQuery("select " + KEY_ALBUM + "," + KEY_ALBUMID
                        + " from " + DB_TABLE
                        + " group by " + KEY_ALBUM, null);

                while (flag_songs.moveToNext()) {
                    map = new HashMap<>();
                    String album = flag_songs.getString(0);
                    int albumid = flag_songs.getInt(1);
                    group_songs = db.query(DB_TABLE, new String[]{
                                    KEY_ID, KEY_TITLE, KEY_ARTIST, KEY_ALBUM, KEY_ALBUMID},
                            KEY_ALBUM + "= ?", new String[]{album}, null, null, null);
                    List<Song> songs = new ArrayList<>();
                    while (group_songs.moveToNext()) {
                        Song song = new Song();
                        song.setId(group_songs.getInt(0));
                        song.setTitle(group_songs.getString(1));
                        song.setArtist(group_songs.getString(2));
                        song.setAlbum(group_songs.getString(3));
                        song.setArtistid(group_songs.getInt(4));
                        songs.add(song);
                    }
                    map.put("ALBUM", album);
                    map.put("COUNT", songs.size());
                    map.put("ALBUM_ID", albumid);
                    map.put("ARTIST", songs.get(0).getArtist());
                    map.put("SONGLIST", songs);
                    csongs.add(map);
                }

                break;
            default:
                break;
        }
        if (group_songs != null)
            group_songs.close();
        flag_songs.close();
        return csongs;
    }

    private static class DBOpenHelper extends SQLiteOpenHelper {

        private static final String DB_CREATE =
                "create table " + DB_TABLE + "(" + KEY_ID + " integer primary key,"
                        + KEY_TITLE + " text not null,"
                        + KEY_ARTIST + " text,"
                        + KEY_ARTISTID + " integer,"
                        + KEY_ALBUM + " text,"
                        + KEY_ALBUMID + " integer,"
                        + KEY_URI + " text);";

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_CREATE);
            onCreate(db);
        }
    }
}
