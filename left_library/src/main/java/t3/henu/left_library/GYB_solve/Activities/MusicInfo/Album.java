package t3.henu.left_library.GYB_solve.Activities.MusicInfo;

import android.os.Parcel;
import android.os.Parcelable;



public class Album implements Parcelable {
    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
    private String id;
    private String name;
    private String picUrl;

    public Album() {
    }

    protected Album(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.picUrl = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.picUrl);
    }
}
