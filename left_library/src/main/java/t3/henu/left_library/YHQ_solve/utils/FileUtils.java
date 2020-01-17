package t3.henu.left_library.YHQ_solve.utils;

import android.text.TextUtils;


/**
 * 文件工具类
 * Created by wcy on 2016/1/3.
 */
public class FileUtils {



    public static String getArtistAndAlbum(String artist, String album) {
        if (TextUtils.isEmpty(artist) && TextUtils.isEmpty(album)) {
            return "";
        } else if (!TextUtils.isEmpty(artist) && TextUtils.isEmpty(album)) {
            return artist;
        } else if (TextUtils.isEmpty(artist) && !TextUtils.isEmpty(album)) {
            return album;
        } else {
            return artist + " - " + album;
        }
    }
}
