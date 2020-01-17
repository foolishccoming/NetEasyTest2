package t3.henu.left_library.GYB_solve.Activities;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import t3.henu.left_library.R;

public class MusicUtils {
    private static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");
    public static List<SongInfo> list;
    public static List<SongInfo> getMp3Infos(Context context) {
        if(list==null){
            list= new LinkedList<SongInfo>();
            // 媒体库查询语句（写一个工具类MusicUtils）

            Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, MediaStore.Audio.AudioColumns.IS_MUSIC);
            getCursor(cursor,list,context);
            Cursor cursor2=context.getContentResolver().query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                    null,null,null,
                    MediaStore.Audio.AudioColumns.IS_MUSIC);
            getCursor(cursor2,list,context);
        }

        //Toast.makeText(context,"list共有："+list.size()+"第35"+list.get(38).getSong(),Toast.LENGTH_LONG).show();
        return list;
    }

    private static void getCursor(Cursor cursor, List<SongInfo> list, Context context) {

        if (cursor != null&&cursor.moveToFirst()) {

            do {
                SongInfo mp3 = new SongInfo();
                mp3.setSong(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                mp3.setSinger( cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                mp3.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                mp3.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                mp3.setSize( cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                mp3.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
                long albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long songId=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                //mp3.setPucUrl(getAlbumArt(context,albumId));
                // mp3.setAlbum_bitmap(getImage(context,albumId));
                mp3.setId(songId);
                mp3.setAlbumId(albumId);
               // mp3.setAlbumUrl(BitmapFactory.decodeFile(getAlbumArt(context,albumId)));
                if (mp3.getSize() > 1000 * 800) {
                    // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
                    if (mp3.getSong().contains("-")) {
                        String[] str = mp3.getSong().split("-");
                        mp3.setSinger( str[0]);
                        mp3.setSong( str[1]);
                    }
                    list.add(mp3);
                }
            }while (cursor.moveToNext());
            // 释放资源
            cursor.close();
        }
    }

    /**
     * 定义一个方法用来格式化获取到的时间
     */
    public static String formatTime(int time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;

        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }

    }
    public static Bitmap getDefaultArtwork(Context context,boolean small) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        if(small){	//返回小图片
            return BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.default_album), null, opts);
        }
        return BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.default_album), null, opts);
    }


    private static String getAlbumArt(Context context, long album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = context.getContentResolver().query(
                Uri.parse(mUriAlbums + "/" + album_id),
                projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        return album_art;
    }


    private static Bitmap getImage(Context context, long album_id) {
        String albumArt = getAlbumArt(context, album_id);
        Bitmap bm = null;
        if (albumArt == null) {
            return null;
        } else {
            bm = BitmapFactory.decodeFile(albumArt);
            BitmapDrawable bmpDraw = new BitmapDrawable(bm);
            return bm;
        }
    }

    /**
     * 从文件当中获取专辑封面位图
     * @param context
     * @param songid
     * @param albumid
     * @return
     */
    private static Bitmap getArtworkFromFile(Context context, long songid, long albumid){
        Bitmap bm = null;
        if(albumid < 0 && songid < 0) {
            throw new IllegalArgumentException("Must specify an album or a song id");
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            FileDescriptor fd = null;
            if(albumid < 0){
                Uri uri = Uri.parse("content://media/external/audio/media/"
                        + songid + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if(pfd != null) {
                    fd = pfd.getFileDescriptor();
                }
            } else {
                Uri uri = ContentUris.withAppendedId(albumArtUri, albumid);
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if(pfd != null) {
                    fd = pfd.getFileDescriptor();
                }
            }
            options.inSampleSize = 1;
            // 只进行大小判断
            options.inJustDecodeBounds = true;
            // 调用此方法得到options得到图片大小
            BitmapFactory.decodeFileDescriptor(fd, null, options);
            // 我们的目标是在800pixel的画面上显示
            // 所以需要调用computeSampleSize得到图片缩放的比例
            options.inSampleSize = 100;
            // 我们得到了缩放的比例，现在开始正式读入Bitmap数据
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            //根据options参数，减少所需要的内存
            bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bm;
    }

    /**
     * 获取专辑封面位图对象
     * @param context
     * @param song_id
     * @param album_id
     * @param allowdefalut
     * @return
     */
    public static Bitmap getArtwork(Context context, long song_id, long album_id, boolean allowdefalut, boolean small){
        if(album_id < 0) {
            if(song_id < 0) {
                Bitmap bm = getArtworkFromFile(context, song_id, -1);
                if(bm != null) {
                    return bm;
                }
            }
            if(allowdefalut) {
                return getDefaultArtwork(context, small);
            }
            return null;
        }
        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(albumArtUri, album_id);
        if(uri != null) {
            InputStream in = null;
            try {
                in = res.openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                //先制定原始大小
                options.inSampleSize = 1;
                //只进行大小判断
                options.inJustDecodeBounds = true;
                //调用此方法得到options得到图片的大小
                BitmapFactory.decodeStream(in, null, options);
                /** 我们的目标是在你N pixel的画面上显示。 所以需要调用computeSampleSize得到图片缩放的比例 **/
                /** 这里的target为800是根据默认专辑图片大小决定的，800只是测试数字但是试验后发现完美的结合 **/
                if(small){
                    options.inSampleSize = computeSampleSize(options, 40);
                } else{
                    options.inSampleSize = computeSampleSize(options, 600);
                }
                // 我们得到了缩放比例，现在开始正式读入Bitmap数据
                options.inJustDecodeBounds = false;
                options.inDither = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                in = res.openInputStream(uri);
                return BitmapFactory.decodeStream(in, null, options);
            } catch (FileNotFoundException e) {
                Bitmap bm = getArtworkFromFile(context, song_id, album_id);
                if(bm != null) {
                    if(bm.getConfig() == null) {
                        bm = bm.copy(Bitmap.Config.RGB_565, false);
                        if(bm == null && allowdefalut) {
                            return getDefaultArtwork(context, small);
                        }
                    }
                } else if(allowdefalut) {
                    bm = getDefaultArtwork(context, small);
                }
                return bm;
            } finally {
                try {
                    if(in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 对图片进行合适的缩放
     * @param options
     * @param target
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options, int target) {
        int w = options.outWidth;
        int h = options.outHeight;
        int candidateW = w / target;
        int candidateH = h / target;
        int candidate = Math.max(candidateW, candidateH);
        if(candidate == 0) {
            return 1;
        }
        if(candidate > 1) {
            if((w > target) && (w / candidate) < target) {
                candidate -= 1;
            }
        }
        if(candidate > 1) {
            if((h > target) && (h / candidate) < target) {
                candidate -= 1;
            }
        }
        return candidate;
    }
}


