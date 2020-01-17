package t3.henu.left_library.GYB_solve.Activities.MusicInfo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;



public class Song implements Parcelable {
    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
    private String id;
    private String name;
    private String picUrl;
    private String audio;
    private List<Artist> artists;
    private Album album;

    public Song() {
    }

    protected Song(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.picUrl = in.readString();
        this.audio = in.readString();
        this.artists = in.createTypedArrayList(Artist.CREATOR);
        this.album = in.readParcelable(Album.class.getClassLoader());
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.picUrl);
        dest.writeString(this.audio);
        dest.writeTypedList(this.artists);
        dest.writeParcelable( this.album, flags);
    }
}

