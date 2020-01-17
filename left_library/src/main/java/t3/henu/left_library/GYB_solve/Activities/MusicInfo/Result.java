package t3.henu.left_library.GYB_solve.Activities.MusicInfo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;



public class Result implements Parcelable {
    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
    private int songCount;
    private List<Song> songs;

    public Result() {
    }

    protected Result(Parcel in) {
        this.songCount = in.readInt();
        this.songs = in.createTypedArrayList(Song.CREATOR);
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
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
        dest.writeInt(this.songCount);
        dest.writeTypedList(this.songs);
    }
}


