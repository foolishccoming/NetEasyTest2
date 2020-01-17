package t3.henu.left_library.GYB_solve.Activities;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


public class SongInfo implements Parcelable {
    public static final Parcelable.Creator<SongInfo> CREATOR = new Parcelable.Creator<SongInfo>() {
        @Override
        public SongInfo createFromParcel(Parcel source) {
            return new SongInfo(source);
        }

        @Override
        public SongInfo[] newArray(int size) {
            return new SongInfo[size];
        }
    };
    private long Id,size,duration,albumId;
    private String song,path,singer,pucUrl,lrc,pic_big,pic_small,album;
    private Bitmap album_bitmap;
    private Bitmap albumUrl;

    public SongInfo() {
    }

    protected SongInfo(Parcel in) {
        this.Id = in.readLong();
        this.size = in.readLong();
        this.duration = in.readLong();
        this.albumId = in.readLong();
        this.song = in.readString();
        this.path = in.readString();
        this.singer = in.readString();
        this.pucUrl = in.readString();
        this.lrc = in.readString();
        this.pic_big = in.readString();
        this.pic_small = in.readString();
        this.album = in.readString();
        this.albumUrl = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static String getAlbumArt(Context context, int album_id) {
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

    public static Bitmap getBitMap(Context context, int album_id) {
        Bitmap bm = null;
        String albumArt = getAlbumArt(context, album_id);
        if (albumArt != null) {
            bm = BitmapFactory.decodeFile(albumArt);
            BitmapDrawable bmpDraw = new BitmapDrawable(bm);
            return bm;
        }
        return null;
    }

    public Bitmap getAlbum_bitmap() {
        return album_bitmap;
    }

    public void setAlbum_bitmap(Bitmap album_bitmap) {
        this.album_bitmap = album_bitmap;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPic_big() {
        return pic_big;
    }

    public void setPic_big(String pic_big) {
        this.pic_big = pic_big;
    }

    public String getPic_small() {
        return pic_small;
    }

    public void setPic_small(String pic_small) {
        this.pic_small = pic_small;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public Bitmap getAlbumUrl() {
        return albumUrl;
    }

    public void setAlbumUrl(Bitmap albumUrl) {
        this.albumUrl = albumUrl;
    }

    public String getPucUrl() {
        return pucUrl;
    }

    public void setPucUrl(String pucUrl) {
        this.pucUrl = pucUrl;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        if(singer!=null&&singer.length()>0){
            this.singer = singer;
        }else{
            this.singer="wi";
        }

    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getSong() {
        //int i=song.lastIndexOf('.');
        //String song1="";
        if(song.contains(".mp3")){
            song=song.replace(".mp3","");
        }
        if(song.contains(".MP3")){
            song=song.replace(".MP3","");
        }

        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.Id);
        dest.writeLong(this.size);
        dest.writeLong(this.duration);
        dest.writeLong(this.albumId);
        dest.writeString(this.song);
        dest.writeString(this.path);
        dest.writeString(this.singer);
        dest.writeString(this.pucUrl);
        dest.writeString(this.lrc);
        dest.writeString(this.pic_big);
        dest.writeString(this.pic_small);
        dest.writeString(this.album);
        dest.writeParcelable(this.albumUrl, flags);
    }
}
