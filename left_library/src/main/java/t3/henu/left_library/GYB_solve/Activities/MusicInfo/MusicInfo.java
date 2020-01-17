package t3.henu.left_library.GYB_solve.Activities.MusicInfo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;



public class MusicInfo implements Parcelable {
    public static final Parcelable.Creator<MusicInfo> CREATOR = new Parcelable.Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel source) {
            return new MusicInfo(source);
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };
    private List<Song> songs;

    public MusicInfo() {
    }


    protected MusicInfo(Parcel in) {
        this.songs = in.createTypedArrayList(Song.CREATOR);
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.songs);
    }
}
